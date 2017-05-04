package kahveci.purchase.domain;

import kahveci.addon.AddOn;
import kahveci.coffee.Coffee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private List<PurchaseItem> items = new LinkedList<>();

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class PurchaseItem {
        private Coffee coffee;
        private Set<AddOn> addons = new HashSet<>();
    }

}
