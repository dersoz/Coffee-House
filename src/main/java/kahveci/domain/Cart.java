package kahveci.domain;

import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private List<PurchaseItem> items = new LinkedList<>();
}
