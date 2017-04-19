package kahveci.business.purchase;

import kahveci.domain.Cart;

public class TotalPriceSavingsRule implements SavingsRule {

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
