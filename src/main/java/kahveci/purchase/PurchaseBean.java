package kahveci.purchase;

import kahveci.addon.AddOn;
import kahveci.coffee.Coffee;
import kahveci.purchase.domain.Cart;
import kahveci.purchase.domain.PurchaseEvent;
import kahveci.purchase.domain.PurchaseResult;
import kahveci.shared.CoffeeHouseEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
public class PurchaseBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private PriceCalculator priceCalculator;

    public PurchaseResult purchase(Cart cart) {
        Cart filledCart = fillCart(cart);
        return calculatePurchaseResult(filledCart);
    }

    public int getCountForKahve(Coffee k) {
        return getPurchaseEvent(k)
                .orElse(new PurchaseEvent(null, 0))
                .getSalesCount();
    }

    private PurchaseResult calculatePurchaseResult(Cart cart) {
        double rawPrice = priceCalculator.calculateRawPrice(cart);
        double savings = priceCalculator.calculateSavings(cart);
        return new PurchaseResult(rawPrice, savings);
    }

    private Cart fillCart(Cart cart) {
        List<Cart.PurchaseItem> items = new LinkedList<>();
        for (Cart.PurchaseItem item : cart.getItems()) {
            long kahveID = item.getCoffee().getId();
            Coffee k = em.find(Coffee.class, kahveID);
            if (k == null) throw new RuntimeException(kahveID + " ID nolu Coffee bulunamadi");
            incrementSalesCount(k);
            Set<AddOn> eSet = new HashSet<>();
            for (AddOn addOn : item.getAddons()) {
                long eklentiID = addOn.getId();
                AddOn e = em.find(AddOn.class, eklentiID);
                if (e == null) throw new RuntimeException(eklentiID + " ID nolu AddOn bulunamadi");
                eSet.add(e);
            }
            items.add(new Cart.PurchaseItem(k, eSet));
        }
        return new Cart(items);
    }

    private void incrementSalesCount(Coffee k) {
        getPurchaseEvent(k)
                .ifPresent(e -> e.setSalesCount(e.getSalesCount() + 1));
    }

    private void addPurchaseEvent(@Observes CoffeeHouseEvent event) {

    }

    private Optional<PurchaseEvent> getPurchaseEvent(Coffee k) {
        return em.createQuery("select p from PurchaseEvent p where p.coffee = :kahve", PurchaseEvent.class)
                .setParameter("kahve", k)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<PurchaseEvent> getAllPurchase() {
        return em.createQuery("select distinct p from PurchaseEvent p", PurchaseEvent.class)
                .getResultList();
    }

}
