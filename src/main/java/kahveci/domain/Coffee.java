package kahveci.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "applicableAddons")
@Builder
@Entity
@Table(name = "COFFEE")
public class Coffee extends BaseEntity {

    private String name;
    private double price;

    @ManyToMany
    @JoinTable(
            joinColumns = {
                    @JoinColumn(name = "COFFEE_ID")
            },
            name = "COFFEE_ADDONS",
            inverseJoinColumns = {
                    @JoinColumn(name = "ADDON_ID")
            }
    )
    private Set<AddOn> applicableAddons = new HashSet<>();

    public Coffee(String name, double price) {
        this.name = name;
        this.price = price;
    }

}
