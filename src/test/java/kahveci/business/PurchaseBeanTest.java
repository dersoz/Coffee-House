package kahveci.business;

import kahveci.domain.*;
import kahveci.shared.ArquillianDeployer;
import kahveci.startup.DbHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static kahveci.business.KahveciBuilders.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class PurchaseBeanTest {

    @Inject
    private PurchaseBean purchaseBean;

    @Inject
    private DbHelper dbHelper;

    @Inject
    private KahveciBean kahveciBean;

    @Before
    public void setUp() {
        dbHelper.clearDb();
        assertTrue(kahveciBean.getAllEklenti().isEmpty());
        assertTrue(kahveciBean.getAllKahveEager().isEmpty());
    }

    @Test
    public void getAllPurchase() throws Exception {
        dbHelper.addInitData();
        Optional<Kahve> americano = dbHelper.findKahveByName("Americano");
        Optional<Eklenti> eklenti = dbHelper.findEklentiByEklentiAndKahveName("Sut", "Americano");
        assertTrue(americano.isPresent());
        assertTrue(eklenti.isPresent());
        Kahve k = americano.get();
        Eklenti e = eklenti.get();
        Cart cart = buildCart(
                buildItemList(
                        buildItem(
                                k,
                                buildEklentiler(
                                        e
                                )
                        )
                )
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
