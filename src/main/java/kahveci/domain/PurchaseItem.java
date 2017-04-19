package kahveci.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PurchaseItem {

    private Coffee coffee;
    private Set<AddOn> addons = new HashSet<>();

}
