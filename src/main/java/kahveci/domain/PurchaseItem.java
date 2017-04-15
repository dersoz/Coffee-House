package kahveci.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseItem {
    private Kahve kahve;
    private Set<Eklenti> addons = new HashSet<>();
}
