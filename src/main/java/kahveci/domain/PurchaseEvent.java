package kahveci.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PURCHASE_EVENT")
public class PurchaseEvent extends BaseEntity {
    @ManyToOne
    private Kahve kahve;
    private int salesCount = 0;
}
