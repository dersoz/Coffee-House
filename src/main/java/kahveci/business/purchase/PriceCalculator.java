package kahveci.business.purchase;

import kahveci.domain.AddOn;
import kahveci.domain.Cart;
import kahveci.domain.PurchaseItem;

import java.util.LinkedList;
import java.util.List;

public class PriceCalculator {

    private static final List<SavingsRule> savingsRules = new LinkedList<>();

    static {
        savingsRules.add(new ThreeOrMoreCoffeeSavingsRule());
        savingsRules.add(new TotalPriceSavingsRule());
    }

    /**
     * Birden fazla indirim uygulanabiliyorsa en buyugunu uygula
     */
    double calculateSavings(Cart cart) {
        double maxVal = 0;
        for (SavingsRule current : savingsRules) {
            double saving = current.apply(cart);
            if (saving > 0 && maxVal < saving)
                maxVal = saving;
        }
        return maxVal;
    }

    double calculateRawPrice(Cart cart) {
        return cart.getItems()
                .stream()
                .map(this::calculateRawPrice)
                .reduce(Double::sum)
                .orElse(0.0);
    }

    double calculateRawPrice(PurchaseItem item) {
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
