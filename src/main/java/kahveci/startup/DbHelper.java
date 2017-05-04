package kahveci.startup;

import kahveci.addon.AddOn;
import kahveci.addon.AddOnCrud;
import kahveci.coffee.Coffee;
import kahveci.coffee.CoffeeCrud;

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
        AddOn e1 = buildAddOn("Sut", 1);
        AddOn e2 = buildAddOn("Su", 0.5);
        AddOn e3 = buildAddOn("Findik Surubu", 1.5);
        AddOn e4 = buildAddOn("Tarcin", 2);
        AddOn e5 = buildAddOn("Limon", 1.2);
        addOnCrud.addAddOns(e1, e2, e3, e4, e5);
        List<Coffee> kahveler = new LinkedList<>();
        kahveler.add(buildCoffee("Americano", 5, e1, e2, e3));
        kahveler.add(buildCoffee("Turkish", 3));
        kahveler.add(buildCoffee("Espresso", 5, e1, e3));
        kahveler.add(buildCoffee("Latte", 6, e3));
        kahveler.add(buildCoffee("Cay", 5, e2, e4, e5));
        coffeeCrud.addKahveler(kahveler);
    }

    private Coffee buildCoffee(String name, double price, AddOn... e) {
        return new Coffee(name, price, new HashSet<>(Arrays.asList(e)));
    }

    private AddOn buildAddOn(String name, double price) {
        return new AddOn(name, price);
    }

    public void clearDb() {
        addOnCrud.findAllAddOn().forEach(this::removeAddOn);
        coffeeCrud.getAllCoffeeEager().forEach(this::removeCoffee);
        coffeeCrud.getAllCoffeeEager().forEach(this::removeCoffee);
    }

    private void removeCoffee(Coffee coffee) {
        em.remove(coffee);
        em.flush();
    }

    private void removeAddOn(AddOn addOn) {
        em.remove(addOn);
        for (Coffee coffee : addOn.getApplicableCoffees()) {
            coffee.getApplicableAddons().remove(addOn);
            em.merge(coffee);
        }
        em.flush();
    }

    public Optional<Coffee> findCoffeeByName(String name) {
        return em.createNamedQuery("kahveci.findKahveByName", Coffee.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<AddOn> findAddOnByNameAndByCoffeeName(String addonName, String coffeeName) {
        return em.createNamedQuery("kahveci.findAddOnByNameAndByCoffeeName", AddOn.class)
                .setParameter("eName", addonName)
                .setParameter("kName", coffeeName)
                .getResultList()
                .stream()
                .findFirst();
    }

}
