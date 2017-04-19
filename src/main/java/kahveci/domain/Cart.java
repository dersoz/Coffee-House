package kahveci.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Cart {
    private List<PurchaseItem> items = new LinkedList<>();
}
