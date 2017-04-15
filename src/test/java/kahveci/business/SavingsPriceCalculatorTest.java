package kahveci.business;

import kahveci.domain.Cart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static kahveci.business.KahveciBuilders.*;

public class SavingsPriceCalculatorTest {

    private PriceCalculator priceCalculator;

    @Before
    public void setUp() {
        priceCalculator = new PriceCalculator();
    }

    // 3'den az ve toplami 12'den azsa
    @Test
    public void uctenAzKahveAlindiVeToplamTutar12denAzIseIndirimYok() {
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

    // 3'den az ve toplami 12'den fazla ise
    @Test
    public void uctenAzKahveAlindiVeToplamTutar12denFazlaIseYuzde25IndirimOlmali() {
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

    // 3'den fazla ve toplami 12'den az ise
    @Test
    public void ucVeFazlaKahveAlindiVeToplamTutar12denAzIseYuzde25IndirimOlmali() {
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

    // 3'den fazla ve toplami 12'den fazla ise
    @Test
    public void ucVeFazlaKahveAlindiVeToplamTutar12denFazlaIseIndirimlerdenMaxOlaniUygulanir() {
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
