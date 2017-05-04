package kahveci.addon;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Transactional
public class AddOnCrud {

    @PersistenceContext
    private EntityManager em;

    public List<AddOn> findAllAddOn() {
        return em.createQuery("select distinct e from AddOn e", AddOn.class)
                .getResultList();
    }

    private void addAddOn(@NotNull AddOn addOn) {
        em.persist(addOn);
    }

    public void addAddOns(@NotNull AddOn... addOns) {
        Arrays.stream(addOns).forEach(this::addAddOn);
    }

}
