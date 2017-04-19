package kahveci.business.eklenti;

import kahveci.domain.AddOn;

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

    public List<AddOn> getAllEklenti() {
        return em.createQuery("select distinct e from AddOn e", AddOn.class)
                .getResultList();
    }

    public void deleteEklenti(AddOn e) {
        em.remove(e);
    }

    public void addEklenti(@NotNull AddOn addOn) {
        em.persist(addOn);
    }

    public void addEklentiler(@NotNull AddOn... eklentiler) {
        Arrays.stream(eklentiler).forEach(this::addEklenti);
    }

}
