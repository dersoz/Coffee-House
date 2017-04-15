package kahveci.business;

import kahveci.domain.Cart;
import kahveci.domain.Eklenti;
import kahveci.domain.PurchaseItem;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class PriceCalculator {

    @Inject
    private Instance<SavingsRule> savingsRules;

    /**
     * Birden fazla indirim uygulanabiliyorsa en buyugunu uygula
     */
    double calculateSavings(Cart cart) {
        double maxVal = 0;
        for (SavingsRule current : savingsRules) {
            double saving = current.apply(cart);
            if (saving > 0) {
                if (maxVal < saving) {
                    maxVal = saving;
                }
            }
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
                .getKahve()
                .getPrice()
                + item
                .getAddons()
                .stream()
                .map(Eklenti::getPrice)
                .reduce(Double::sum)
                .orElse(0.0);
    }

}
