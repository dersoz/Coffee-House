package kahveci.business;

import kahveci.domain.Cart;

import javax.inject.Inject;
import java.util.stream.DoubleStream;

public class ThreeOrMoreCoffeeSavingsRule implements SavingsRule {

    @Inject
    private PriceCalculator priceCalculator;

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
