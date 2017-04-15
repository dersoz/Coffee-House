package kahveci.business;

import kahveci.domain.Cart;
import kahveci.domain.PurchaseItem;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static kahveci.business.KahveciBuilders.*;
import static org.junit.Assert.assertEquals;

public class RawPriceCalculatorTest {

    private PriceCalculator priceCalculator;

    @Before
    public void setUp() {
        priceCalculator = new PriceCalculator();
    }

    /**
     * Item icinde sadece coffee varsa onun price donmeli
     */
    @Test
    public void eklentisizKahveIcinSadeceKahveFiyatiDonmeli() {
        PurchaseItem pi = new PurchaseItem(
                buildKahve("Espresso", 5),
                Collections.emptySet()
        );
        assertEquals(5, priceCalculator.calculateRawPrice(pi), 0.001);
    }

    /**
     * Item icinde hem kahve hem eklenti varsa hepsinin toplam fiyati donmeli
     */
    @Test
    public void eklentiliKahveIcinKahveVeEklentiToplamFiyatiDonmeli() {
        PurchaseItem pi = new PurchaseItem(
                buildKahve("Espresso", 5),
                buildEklentiler(
                        buildEklenti("Su", 0.5),
                        buildEklenti("Sut", 1),
                        buildEklenti("Findik Surubu", 1.5)
                )
        );
        assertEquals(8, priceCalculator.calculateRawPrice(pi), 0.001);
    }

    /**
     * Cart icinde item yoksa 0 donmeli
     */
    @Test
    public void bosCartIcinPriceSifirOlmali() {
        Cart cart = buildCart(
                Collections.emptyList()
        );
        assertEquals(0.0, priceCalculator.calculateRawPrice(cart), 0.001);
    }

    /**
     * Cart icinde item listesi varsa her bir item toplami donmeli
     */
    @Test
    public void cartIcindeListeVarsaHerBirElemanToplamiDonmeli() {
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