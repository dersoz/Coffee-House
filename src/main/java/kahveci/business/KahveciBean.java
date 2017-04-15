package kahveci.business;

import kahveci.domain.Eklenti;
import kahveci.domain.Kahve;
import kahveci.domain.PurchaseEvent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Transactional
public class KahveciBean {

    @PersistenceContext
    private EntityManager em;

    public List<Eklenti> getAllEklenti() {
        return em.createQuery("select e from Eklenti e", Eklenti.class)
                .getResultList();
    }

    public List<Kahve> getAllKahve() {
        return em.createQuery("select distinct k from Kahve k join fetch k.applicableAddons", Kahve.class)
                .getResultList();
    }

    public Long getAllKahveCount() {
        return em.createQuery("select count(k) from Kahve k", Long.class)
                .getSingleResult();
    }

    public void addKahve(@NotNull Kahve kahve) {
        em.persist(kahve);
        PurchaseEvent event = new PurchaseEvent(kahve, 0);
        em.persist(event);
    }

    public void addKahveler(@NotNull Kahve... kahveler) {
        Arrays.stream(kahveler).forEach(this::addKahve);
    }

    public void addEklenti(@NotNull Eklenti eklenti) {
        em.persist(eklenti);
    }

    public void addEklentiler(@NotNull Eklenti... eklentiler) {
        Arrays.stream(eklentiler).forEach(this::addEklenti);
    }

    public void addEklentiler(@NotNull Collection<Eklenti> eklentiler) {
        eklentiler.forEach(em::persist);
    }

}
