package kahveci.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

}
