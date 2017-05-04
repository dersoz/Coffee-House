package kahveci.purchase;

import kahveci.addon.AddOn;
import kahveci.purchase.domain.Cart;
import kahveci.purchase.domain.DiscountRule;
import kahveci.purchase.domain.ThreeOrMoreCoffeeDiscountRule;
import kahveci.purchase.domain.TotalPriceDiscountRule;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.DoubleStream;

public class PriceCalculator {

    private static final List<DiscountRule> DISCOUNT_RULES = new LinkedList<>();

    static {
        DISCOUNT_RULES.add(new ThreeOrMoreCoffeeDiscountRule());
        DISCOUNT_RULES.add(new TotalPriceDiscountRule());
    }

    /**
     * If more than once discount is applicable, chose the maximum amount
     */
    public double calculateSavings(Cart cart) {
        DoubleStream.Builder dsBuilder = DoubleStream.builder();
        DISCOUNT_RULES
                .stream()
                .map(r -> r.apply(cart))
                .forEach(dsBuilder::accept);
        return dsBuilder
                .build()
                .max()
                .orElse(0.0);
    }

    private double calculateDiscount(Cart cart) {
        double maxVal = 0;
        for (DiscountRule current : DISCOUNT_RULES) {
            double saving = current.apply(cart);
            if (saving > 0 && maxVal < saving)
                maxVal = saving;
        }
        return maxVal;
    }

    public double calculateRawPrice(Cart cart) {
        return cart.getItems()
                .stream()
                .map(this::calculateRawPrice)
                .reduce(Double::sum)
                .orElse(0.0);
    }

    public double calculateRawPrice(Cart.PurchaseItem item) {
        return item
                .getCoffee()
                .getPrice()
                + item
                .getAddons()
                .stream()
                .map(AddOn::getPrice)
                .reduce(Double::sum)
                .orElse(0.0);
    }

}
