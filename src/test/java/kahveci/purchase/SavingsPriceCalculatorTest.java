package kahveci.purchase;

import kahveci.purchase.domain.Cart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static kahveci.shared.TestDataBuilders.*;

public class SavingsPriceCalculatorTest {

    private PriceCalculator priceCalculator;

    @Before
    public void setUp() {
        priceCalculator = new PriceCalculator();
    }

    // If less than 3 and total price is less than 12
    @Test
    public void ifLessThan3Coffee_And_ifTotalPriceLessThan12_noDiscount() {
        Cart cart = buildCart(
                buildItemList(
                        buildItem(
                                buildKahve(
                                        "Espresso",
                                        5
                                ),
                                Collections.emptySet()
                        )
                )
        );
        Assert.assertEquals(5.0, priceCalculator.calculateRawPrice(cart), 0.001);
        Assert.assertEquals(0.0, priceCalculator.calculateSavings(cart), 0.001);
    }

    // If less than 3 coffee and total price is more than 12
    @Test
    public void ifLessThan3Coffee_and_ifTotalPriceMoreThan12_25PercentDiscount() {
        Cart cart = buildCart(
                buildItemList(
                        buildItem(
                                buildKahve(
                                        "Espresso",
                                        5
                                ),
                                buildEklentiler(
                                        buildEklenti(
                                                "Sut",
                                                1
                                        )
                                )
                        ),
                        buildItem(
                                buildKahve(
                                        "Turkish",
                                        8
                                ),
                                Collections.emptySet()
                        )
                )
        );
        Assert.assertEquals(14.0, priceCalculator.calculateRawPrice(cart), 0.001);
        Assert.assertEquals(3.5, priceCalculator.calculateSavings(cart), 0.001);
    }

    // If more than 3 coffee and total price is more than 12
    @Test
    public void ifMoreThan3Coffee_and_totalPriceIsMoreThan12_25PercentDiscount() {
        Cart cart = buildCart(
                buildItemList(
                        buildItem(
                                buildKahve(
                                        "Espresso",
                                        2
                                ),
                                buildEklentiler(
                                        buildEklenti(
                                                "Sut",
                                                1
                                        )
                                )
                        ),
                        buildItem(
                                buildKahve(
                                        "Turkish",
                                        2.5
                                ),
                                Collections.emptySet()
                        ),
                        buildItem(
                                buildKahve(
                                        "Americano",
                                        3.5
                                ),
                                Collections.emptySet()
                        )
                )
        );
        Assert.assertEquals(9.0, priceCalculator.calculateRawPrice(cart), 0.001);
        Assert.assertEquals(2.5, priceCalculator.calculateSavings(cart), 0.001);
    }

    // If more than 3 coffee and total price is more than 12 max discount amount is max priced coffee
    @Test
    public void ifMoreThan3Coffee_and_ifTotalPriceMoreThan12_maxPricedCoffeeIsDiscount() {
        Cart cart = buildCart(
                buildItemList(
                        buildItem(
                                buildKahve(
                                        "Espresso",
                                        6
                                ),
                                buildEklentiler(
                                        buildEklenti(
                                                "Sut",
                                                1
                                        )
                                )
                        ),
                        buildItem(
                                buildKahve(
                                        "Turkish",
                                        15.5
                                ),
                                Collections.emptySet()
                        ),
                        buildItem(
                                buildKahve(
                                        "Americano",
                                        8.5
                                ),
                                Collections.emptySet()
                        ),
                        buildItem(
                                buildKahve(
                                        "Alta Vista",
                                        6.5
                                ),
                                buildEklentiler(
                                        buildEklenti(
                                                "Findik Surubu",
                                                1
                                        )
                                )
                        )
                )
        );
        Assert.assertEquals(38.5, priceCalculator.calculateRawPrice(cart), 0.001);
        Assert.assertEquals(9.625, priceCalculator.calculateSavings(cart), 0.001);
    }

}
