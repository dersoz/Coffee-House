package kahveci.purchase;

import kahveci.addon.AddOn;
import kahveci.coffee.Coffee;
import kahveci.purchase.domain.Cart;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static kahveci.shared.TestDataBuilders.*;
import static org.junit.Assert.assertEquals;

public class RawPriceCalculatorTest {

    private PriceCalculator priceCalculator;

    @Before
    public void setUp() {
        priceCalculator = new PriceCalculator();
    }

    @Test
    public void ifNoAddOnsSelected_totalPrice_shouldBe_coffeePrice() {
        Cart.PurchaseItem pi = new Cart.PurchaseItem(
                new Coffee("Espresso", 5),
                Collections.emptySet()
        );
        assertEquals(5, priceCalculator.calculateRawPrice(pi), 0.001);
    }

    @Test
    public void ifAdditivesSelected_totalPrice_shouldBe_coffeePrice_PLUS_additivesPrices() {
        Cart.PurchaseItem pi = new Cart.PurchaseItem(
                new Coffee("Espresso", 5),
                new HashSet<>(
                        Arrays.asList(
                                new AddOn("Su", 0.5),
                                new AddOn("Sut", 1),
                                new AddOn("Findik Surubu", 1.5)
                        )
                )
        );
        assertEquals(8, priceCalculator.calculateRawPrice(pi), 0.001);
    }

    @Test
    public void ifCart_IS_empty_total_shouldBe_ZERO() {
        Cart cart = buildCart(
                Collections.emptyList()
        );
        assertEquals(0.0, priceCalculator.calculateRawPrice(cart), 0.001);
    }

    @Test
    public void ifNonEmptyCart_totalPrice_shouldBe_sumOfAll() {
        Cart cart = buildCart(
                buildItemList(
                        buildItem(
                                buildKahve("Espresso", 5),
                                buildEklentiler(
                                        buildEklenti("Su", 0.5),
                                        buildEklenti("Sut", 1),
                                        buildEklenti("Findik Surubu", 1.5)
                                )
                        ),
                        buildItem(
                                buildKahve("Turkish", 3),
                                Collections.emptySet()
                        ),
                        buildItem(
                                buildKahve("Latte", 7),
                                buildEklentiler(
                                        buildEklenti("Findik Surubu", 1.5)
                                )
                        )
                )
        );
        assertEquals(19.5, priceCalculator.calculateRawPrice(cart), 0.001);
    }

//    Cart icinde tek item varsa sadece onun price donmeli
}