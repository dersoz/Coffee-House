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
@EqualsAndHashCode(callSuper = true, exclude = "applicableCoffees")
@Entity
@Table(name = "ADDON")
public class AddOn extends BaseEntity {

    private String name;
    private double price;

    @XmlTransient
    @ManyToMany(mappedBy = "applicableAddons")
    private Set<Coffee> applicableCoffees = new HashSet<>();

    public AddOn(String name, double price) {
        this.name = name;
        this.price = price;
    }

}
