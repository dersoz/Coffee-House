package kahveci.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseResult {
    private double rawPrice;
    private double savings;
}
