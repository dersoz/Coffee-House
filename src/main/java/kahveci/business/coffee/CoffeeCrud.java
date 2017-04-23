package kahveci.business.coffee;

import kahveci.domain.Coffee;
import kahveci.domain.PurchaseEvent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional
public class CoffeeCrud {

    @PersistenceContext
    private EntityManager em;

    public List<Coffee> getAllCoffeeEager() {
        return em.createQuery("select distinct k from Coffee k join fetch k.applicableAddons", Coffee.class)
                .getResultList();
    }

    public void addKahve(@NotNull Coffee coffee) {
        em.persist(coffee);
        PurchaseEvent event = findPurchaseEventOfCoffee(coffee)
                .orElse(new PurchaseEvent(coffee, 0));
        em.persist(event);
    }

    private Optional<PurchaseEvent> findPurchaseEventOfCoffee(@NotNull Coffee coffee) {
        return em.createQuery("select p from PurchaseEvent p where p.coffee = :kahve", PurchaseEvent.class)
                .setParameter("kahve", coffee)
                .getResultList()
                .stream()
                .findFirst();
    }

    public void addKahveler(@NotNull Coffee... kahveler) {
        Arrays.stream(kahveler).forEach(this::addKahve);
    }

    public void addKahveler(@NotNull Collection<Coffee> kahveler) {
        kahveler.forEach(this::addKahve);
    }

}
