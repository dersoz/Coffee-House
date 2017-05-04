package kahveci.purchase;

import kahveci.addon.AddOn;
import kahveci.addon.AddOnCrud;
import kahveci.coffee.Coffee;
import kahveci.coffee.CoffeeCrud;
import kahveci.purchase.domain.Cart;
import kahveci.purchase.domain.PurchaseEvent;
import kahveci.purchase.domain.PurchaseResult;
import kahveci.shared.ArquillianDeployer;
import kahveci.startup.DbHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class PurchaseBeanTest {

    @Inject
    private PurchaseBean purchaseBean;

    @Inject
    private DbHelper dbHelper;

    @Inject
    AddOnCrud addOnCrud;

    @Inject
    CoffeeCrud coffeeCrud;

    @Before
    public void setUp() {
        dbHelper.clearDb();
        assertTrue(addOnCrud.findAllAddOn().isEmpty());
        assertTrue(coffeeCrud.getAllCoffeeEager().isEmpty());
    }

    @Test
    public void getAllPurchase() throws Exception {
        dbHelper.addInitData();
        Optional<Coffee> americano = dbHelper.findCoffeeByName("Americano");
        Optional<AddOn> eklenti = dbHelper.findAddOnByNameAndByCoffeeName("Sut", "Americano");
        assertTrue(americano.isPresent());
        assertTrue(eklenti.isPresent());
        Coffee k = americano.get();
        AddOn e = eklenti.get();
        Cart cart = new Cart(
                Collections.singletonList(new Cart.PurchaseItem(k, Collections.singleton(e)))
        );
        PurchaseResult purchaseResult = purchaseBean.purchase(cart);
        assertNotEquals(0, purchaseResult.getRawPrice(), 0.001);
        List<PurchaseEvent> allPurchase = purchaseBean.getAllPurchase();
        allPurchase.forEach(System.out::println);
    }

    @Deployment
    public static WebArchive createDeployment() {
        return ArquillianDeployer.createDeployment();
    }

}
