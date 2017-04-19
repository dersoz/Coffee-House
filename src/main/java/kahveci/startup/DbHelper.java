package kahveci.startup;

import kahveci.business.coffee.CoffeeCrud;
import kahveci.business.eklenti.AddOnCrud;
import kahveci.domain.AddOn;
import kahveci.domain.Coffee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
public class DbHelper {

    @Inject
    private AddOnCrud addOnCrud;

    @Inject
    private CoffeeCrud coffeeCrud;

    @PersistenceContext
    private EntityManager em;

    public void addInitData() {
        AddOn e1 = buildEklenti("Sut", 1);
        AddOn e2 = buildEklenti("Su", 0.5);
        AddOn e3 = buildEklenti("Findik Surubu", 1.5);
        AddOn e4 = buildEklenti("Tarcin", 2);
        AddOn e5 = buildEklenti("Limon", 1.2);
        addOnCrud.addEklentiler(e1, e2, e3, e4, e5);
        List<Coffee> kahveler = new LinkedList<>();
        kahveler.add(buildKahve("Americano", 5, e1, e2, e3));
        kahveler.add(buildKahve("Turkish", 3));
        kahveler.add(buildKahve("Espresso", 5, e1, e3));
        kahveler.add(buildKahve("Latte", 6, e3));
        kahveler.add(buildKahve("Cay", 5, e2, e4, e5));
        coffeeCrud.addKahveler(kahveler);
    }

    private Coffee buildKahve(String name, double price, AddOn... e) {
        return new Coffee(name, price, new HashSet<>(Arrays.asList(e)));
    }

    private AddOn buildEklenti(String name, double price) {
        return new AddOn(name, price);
    }

    public void clearDb() {
        addOnCrud.getAllEklenti().forEach(this::removeEklenti);
        coffeeCrud.getAllKahveEager().forEach(this::removeKahve);
        coffeeCrud.getAllKahveEager().forEach(this::removeKahve);
    }

    private void removeKahve(Coffee coffee) {
        em.remove(coffee);
        em.flush();
    }

    private void removeEklenti(AddOn addOn) {
        em.remove(addOn);
        for (Coffee coffee : addOn.getApplicableCoffees()) {
            coffee.getApplicableAddons().remove(addOn);
            em.merge(coffee);
        }
        em.flush();
    }

    public Optional<Coffee> findKahveByName(String name) {
        return em.createQuery("select k from Coffee k where k.name = :name", Coffee.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<AddOn> findEklentiByEklentiAndKahveName(String addonName, String coffeeName) {
        return em.createQuery("select e from AddOn e, Coffee k where e.name = :eName and k.name = :kName and e member of k.applicableAddons", AddOn.class)
                .setParameter("eName", addonName)
                .setParameter("kName", coffeeName)
                .getResultList()
                .stream()
                .findFirst();
    }

}
