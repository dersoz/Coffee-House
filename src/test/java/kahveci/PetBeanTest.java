//package dersoz.ska.business.pets;
//
//import kahveci.shared.ArquillianDeployer;
//import kahveci.shared.TestHelperBean;
//import dersoz.ska.domain.Cat;
//import dersoz.ska.domain.Owner;
//import dersoz.ska.domain.Pet;
//import dersoz.ska.domain.PetOwning;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.time.LocalDateTime;
//import java.util.LinkedList;
//import java.util.List;
//
//@RunWith(Arquillian.class)
////@DefaultDeployment(type = DefaultDeployment.Type.JAR)
//public class PetBeanTest {
//
//    @Inject
//    private PetBean petBean;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Inject
//    private TestHelperBean testHelperBean;
//
//    @Deployment
//    public static WebArchive createDeployment() {
//        return ArquillianDeployer.createDeployment();
//    }
//
//    @Before
//    public void startUp() {
//        testHelperBean.clearDB();
//    }
//
//    @Test
//    public void shouldAddCat() {
//        Assert.assertTrue(petBean.findAllPets().isEmpty());
//        List<Cat> cats = catsToAdd();
//        cats.forEach(petBean::addPet);
//        Assert.assertEquals(cats.size(), petBean.findAllPets().size());
//        Assert.assertEquals(cats.size(), petBean.findAllCats().size());
//        Assert.assertEquals(0, petBean.findAllDogs().size());
//    }
//
//    @Test
//    public void shouldAddOwners() {
//        testHelperBean.assertTableSizeIs(0, Owner.class);
//        List<Owner> owners = ownersToAdd();
//        owners.forEach(petBean::addOwner);
//        testHelperBean.assertTableSizeIs(owners.size(), Owner.class);
//    }
//
//    @Test
//    public void shouldOwnPet() {
//        addAllTestData();
//        Pet p = getPetByName("Balkiz");
//        Assert.assertNotNull(p);
//        Owner o = getOwnerByName("Ozan");
//        Assert.assertNotNull(o);
//        testHelperBean.assertTableSizeIs(0, PetOwning.class);
//        petBean.own(p, o);
//        testHelperBean.assertTableSizeIs(1, PetOwning.class);
//    }
//
//    private Pet getPetByName(String petName) {
//        return em.createQuery("select p from Pet p where p.name = :name", Pet.class)
//                .setParameter("name", petName)
//                .getResultList()
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Pet could not be found"));
//    }
//
//    private Owner getOwnerByName(String ownerName) {
//        return em.createQuery("select o from Owner o where o.name = :name", Owner.class)
//                .setParameter("name", ownerName)
//                .getResultList()
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Owner could not be found"));
//    }
//
//    private void addAllTestData() {
//        ownersToAdd().forEach(testHelperBean::add);
//        catsToAdd().forEach(testHelperBean::add);
//    }
//
//    private List<Cat> catsToAdd() {
//        List<Cat> res = new LinkedList<>();
//        res.add(new Cat(
//                "Balkiz",
//                LocalDateTime.of(2012, 4, 5, 11, 31),
//                "Siyam"
//        ));
//        res.add(new Cat(
//                "Prenses",
//                LocalDateTime.of(2014, 12, 7, 11, 31),
//                "Ankara"
//        ));
//        return res;
//    }
//
//    private List<Owner> ownersToAdd() {
//        List<Owner> res = new LinkedList<>();
//        res.add(new Owner(
//                "Dogan",
//                "Ersoz",
//                "+90-505-444-4444"
//        ));
//        res.add(new Owner(
//                "Ozan",
//                "Ersoz",
//                "+90-505-555-5555"
//        ));
//        res.add(new Owner(
//                "Teoman",
//                "Ersoz",
//                "+90-505-666-6666"
//        ));
//        return res;
//    }
//
//}