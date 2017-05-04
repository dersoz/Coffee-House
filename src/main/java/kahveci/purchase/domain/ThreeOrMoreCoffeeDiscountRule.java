package kahveci.purchase.domain;

import kahveci.purchase.PriceCalculator;

import java.util.stream.DoubleStream;

/**
 * If there are 3 or more coffee in the cart
 * The cart is eligible for a discount with an amount of minimum priced coffee in the cart
 */
public class ThreeOrMoreCoffeeDiscountRule implements DiscountRule {

    private PriceCalculator priceCalculator = new PriceCalculator();

    @Override
    public double apply(Cart cart) {
        DoubleStream.Builder dsBuilder = DoubleStream.builder();
        if (cart.getItems().size() >= 3) {
            cart.getItems()
                    .stream()
                    .map(i -> priceCalculator.calculateRawPrice(i))
                    .forEach(dsBuilder::accept);
            return dsBuilder
                    .build()
                    .min()
                    .orElse(0.0);
        }
        return 0.0;
    }

}
