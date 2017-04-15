package kahveci.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, exclude = "applicableCoffees")
@Entity
@Table(name = "EKLENTI")
public class Eklenti extends BaseEntity {
    private String name;
    private double price;

    @XmlTransient
    @ManyToMany(mappedBy = "applicableAddons")
    private Set<Kahve> applicableCoffees = new HashSet<>();
}
