package kahveci.business;

import kahveci.domain.*;

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

    public int getCountForKahve(Kahve k) {
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
        List<PurchaseItem> items = new LinkedList<>();
        for (PurchaseItem item : cart.getItems()) {
            long kahveID = item.getKahve().getId();
            Kahve k = em.find(Kahve.class, kahveID);
            if (k == null) throw new RuntimeException(kahveID + " ID nolu Kahve bulunamadi");
            incrementSalesCount(k);
            Set<Eklenti> eSet = new HashSet<>();
            for (Eklenti eklenti : item.getAddons()) {
                long eklentiID = eklenti.getId();
                Eklenti e = em.find(Eklenti.class, eklentiID);
                if (e == null) throw new RuntimeException(eklentiID + " ID nolu Eklenti bulunamadi");
                eSet.add(e);
            }
            items.add(new PurchaseItem(k, eSet));
        }
        return new Cart(items);
    }

    private void incrementSalesCount(Kahve k) {
        getPurchaseEvent(k)
                .ifPresent(e -> e.setSalesCount(e.getSalesCount() + 1));
    }

    private Optional<PurchaseEvent> getPurchaseEvent(Kahve k) {
        return em.createQuery("select p from PurchaseEvent p where p.kahve = :kahve", PurchaseEvent.class)
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
