package kahveci.business;

import kahveci.domain.Cart;
import kahveci.domain.PurchaseResult;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
public class PurchaseBean {

    @Inject
    private PriceCalculator priceCalculator;

    public PurchaseResult purchase(Cart cart) {
        // TODO: Persist something to purchase
        return calculatePurchaseResult(cart);
    }

    private PurchaseResult calculatePurchaseResult(Cart cart) {
        double rawPrice = priceCalculator.calculateRawPrice(cart);
        double savings = priceCalculator.calculateSavings(cart);
        return new PurchaseResult(rawPrice, savings);
    }

}
