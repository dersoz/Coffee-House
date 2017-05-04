package kahveci.purchase.domain;

import kahveci.purchase.PriceCalculator;

/**
 * If the total price is more than a certain amount
 * The cart is eligible for 25 percent discount
 */
public class TotalPriceDiscountRule implements DiscountRule {

    private PriceCalculator priceCalculator = new PriceCalculator();

    private final int savingsThreshold = 12;
    private final double savingPercentage = 0.25;

    @Override
    public double apply(Cart cart) {
        double rawPrice = priceCalculator.calculateRawPrice(cart);
        if (rawPrice > savingsThreshold) {
            return rawPrice * savingPercentage;
        }
        return 0.0;
    }

}
