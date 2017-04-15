package kahveci.shared;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class DbHelper {

    @PersistenceContext
    private EntityManager em;

    public void clearDB() {
        em.createNamedQuery("pets.deletePetOwnings").executeUpdate();
        em.createNamedQuery("pets.deleteOwners").executeUpdate();
        em.createNamedQuery("pets.deletePets").executeUpdate();
    }

}
