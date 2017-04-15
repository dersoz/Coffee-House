package kahveci.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, exclude = "applicableAddons")
@Entity
@Table(name = "KAHVE")
public class Kahve extends BaseEntity {

    private String name;
    private double price;

    @ManyToMany
    @JoinTable(
            joinColumns = {
                    @JoinColumn(name = "KAHVE_ID")
            },
            name = "KAHVE_EKLENTI",
            inverseJoinColumns = {
                    @JoinColumn(name = "EKLENTI_ID")
            }
    )
    private Set<Eklenti> applicableAddons = new HashSet<>();

}
