package kahveci.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Cart {
    private List<PurchaseItem> items = new LinkedList<>();
}
