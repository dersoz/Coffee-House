package kahveci.startup;

import kahveci.business.KahveciBean;
import kahveci.domain.Eklenti;
import kahveci.domain.Kahve;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

import static kahveci.business.KahveciBuilders.buildEklenti;
import static kahveci.business.KahveciBuilders.buildKahve;

@Transactional
public class DbHelper {

    @Inject
    private KahveciBean kahveciBean;

    @PersistenceContext
    private EntityManager em;

    void addInitData() {
        Eklenti e1 = buildEklenti("Sut", 1);
        Eklenti e2 = buildEklenti("Su", 0.5);
        Eklenti e3 = buildEklenti("Findik Surubu", 1.5);
        Eklenti e4 = buildEklenti("Tarcin", 2);
        Eklenti e5 = buildEklenti("Limon", 1.2);
        kahveciBean.addEklentiler(e1, e2, e3, e4, e5);
        List<Kahve> kahveler = new LinkedList<>();
        kahveler.add(buildKahve("Americano", 5, e1, e2, e3));
        kahveler.add(buildKahve("Turkish", 3));
        kahveler.add(buildKahve("Espresso", 5, e1, e3));
        kahveler.add(buildKahve("Latte", 6, e3));
        kahveler.add(buildKahve("Cay", 5, e2, e4, e5));
        kahveciBean.addKahveler(kahveler);
    }

    public void clearDb() {
        kahveciBean.getAllEklenti().forEach(this::removeEklenti);
        kahveciBean.getAllKahve().forEach(this::removeKahve);
    }

    private void removeKahve(Kahve kahve) {
        em.remove(kahve);
        em.flush();
    }

    private void removeEklenti(Eklenti eklenti) {
        em.remove(eklenti);
        for (Kahve kahve : eklenti.getApplicableCoffees()) {
            kahve.getApplicableAddons().remove(eklenti);
            em.merge(kahve);
        }
        em.flush();
    }

}
