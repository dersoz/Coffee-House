package kahveci.shared;

import kahveci.domain.BaseEntity;
import org.junit.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class TestHelperBean {

    @PersistenceContext
    private EntityManager em;

    public void clearDB() {
        em.createQuery("delete from Eklenti ").executeUpdate();
        em.createQuery("delete from Kahve ").executeUpdate();
    }

    public <T extends BaseEntity> void assertTableSizeIs(int expectedSize, Class<T> clazz) {
        List<T> list = em.createQuery("select e from " + clazz.getSimpleName() + " e", clazz).getResultList();
        Assert.assertEquals(expectedSize, list.size());
    }

    public Long getAllKahveCount() {
        return em.createQuery("select count(k) from Kahve k", Long.class)
                .getSingleResult();
    }

    public <T extends BaseEntity> void add(T entity) {
        em.persist(entity);
    }

}
