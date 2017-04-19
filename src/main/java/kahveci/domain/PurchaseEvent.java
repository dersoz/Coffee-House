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
    private Coffee coffee;
    private int salesCount = 0;

    @Override
    public String toString() {
        return "PurchaseEvent{" +
                "kahveName=" + coffee.getName() +
                ", salesCount=" + salesCount +
                '}';
    }

}
