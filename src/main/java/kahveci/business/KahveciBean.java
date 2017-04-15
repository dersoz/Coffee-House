package kahveci.business;

import kahveci.domain.Eklenti;
import kahveci.domain.Kahve;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
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
        return em.createQuery("select k from Kahve k join fetch k.applicableAddons", Kahve.class)
                .getResultList();
    }

    public Long getAllKahveCount() {
        return em.createQuery("select count(k) from Kahve k", Long.class)
                .getSingleResult();
    }

    public void addKahve(Kahve kahve) {
        em.persist(kahve);
    }

    public void addEklentiler(@NotNull Eklenti... eklentiler) {
        for (Eklenti eklenti : eklentiler) {
            em.persist(eklenti);
        }
    }

    public void addEklentiler(@NotNull Collection<Eklenti> eklentiler) {
        eklentiler.forEach(em::persist);
    }

}
