package kahveci.shared;

import kahveci.domain.AddOn;
import kahveci.domain.Cart;
import kahveci.domain.Coffee;
import kahveci.domain.PurchaseItem;

import java.util.*;

public final class TestDataBuilders {

    public static List<PurchaseItem> buildItemList(PurchaseItem... items) {
        return new LinkedList<>(Arrays.asList(items));
    }

    public static PurchaseItem buildItem(Coffee coffee, Set<AddOn> eklentiler) {
        return new PurchaseItem(coffee, eklentiler);
    }

    public static Cart buildCart(List<PurchaseItem> items) {
        return new Cart(items);
    }

    public static Set<AddOn> buildEklentiler(AddOn... eklentiler) {
        return new HashSet<>(Arrays.asList(eklentiler));
    }

    public static AddOn buildEklenti(String name, double price) {
        return new AddOn(name, price);
    }

    public static Coffee buildKahve(String name, double price, AddOn... eList) {
        return Coffee.builder()
                .name(name)
                .price(price)
                .applicableAddons(new HashSet<>(Arrays.asList(eList)))
                .build();
    }

    private TestDataBuilders() {
    }
}
