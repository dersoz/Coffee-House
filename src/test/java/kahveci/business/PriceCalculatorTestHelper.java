package kahveci.business;

import kahveci.domain.Cart;
import kahveci.domain.Eklenti;
import kahveci.domain.Kahve;
import kahveci.domain.PurchaseItem;

import java.util.*;

public final class PriceCalculatorTestHelper {

    static List<PurchaseItem> buildItemList(PurchaseItem... items) {
        return new LinkedList<>(Arrays.asList(items));
    }

    static PurchaseItem buildItem(Kahve kahve, Set<Eklenti> eklentiler) {
        return new PurchaseItem(kahve, eklentiler);
    }

    static Cart buildCart(List<PurchaseItem> items) {
        return new Cart(items);
    }

    static Set<Eklenti> buildEklentiler(Eklenti... eklentiler) {
        return new HashSet<>(Arrays.asList(eklentiler));
    }

    static Eklenti buildEklenti(String name, double price) {
        return Eklenti.builder()
                .name(name)
                .price(price)
                .build();
    }

    static Kahve buildKahve(String name, double price) {
        return Kahve.builder()
                .name(name)
                .price(price)
                .build();
    }

    private PriceCalculatorTestHelper() {
    }
}
