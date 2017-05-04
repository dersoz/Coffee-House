package kahveci;

import kahveci.addon.AddOn;
import kahveci.addon.AddOnCrud;
import kahveci.coffee.Coffee;
import kahveci.coffee.CoffeeCrud;
import kahveci.shared.ArquillianDeployer;
import kahveci.startup.DbHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(Arquillian.class)
public class CrudTest {

    @Inject
    private CoffeeCrud coffeeCrud;

    @Inject
    private AddOnCrud addOnCrud;

    @Inject
    private DbHelper dbHelper;

    @Deployment
    public static WebArchive createDeployment() {
        return ArquillianDeployer.createDeployment();
    }

    @Before
    public void setUp() {
        dbHelper.clearDb();
        Assert.assertTrue(addOnCrud.findAllAddOn().isEmpty());
        Assert.assertTrue(coffeeCrud.getAllCoffeeEager().isEmpty());
    }

    @Test
    public void shouldAddEklentiAndKahve() {
        Set<AddOn> eklentiler = getEklentiler();
        Assert.assertTrue(coffeeCrud.getAllCoffeeEager().isEmpty());
        Coffee k1 = buildKahve("Turk Kahvesi", 5);
        k1.setApplicableAddons(eklentiler);
        coffeeCrud.addKahve(k1);
        Assert.assertFalse(coffeeCrud.getAllCoffeeEager().isEmpty());
        Assert.assertEquals(1, coffeeCrud.getAllCoffeeEager().size());
    }

    @NotNull
    private Set<AddOn> getEklentiler() {
        Assert.assertTrue(addOnCrud.findAllAddOn().isEmpty());
        AddOn e1 = buildEklenti("Limon", 1);
        AddOn e2 = buildEklenti("Tarcin", 0.5);
        Set<AddOn> eklentiler = new HashSet<>(Arrays.asList(e1, e2));
        addOnCrud.addAddOns(e1, e2);
        Assert.assertFalse(addOnCrud.findAllAddOn().isEmpty());
        Assert.assertEquals(2, addOnCrud.findAllAddOn().size());
        return eklentiler;
    }

    private Coffee buildKahve(String name, double price) {
        return Coffee.builder()
                .name(name)
                .price(price)
                .build();
    }

    private AddOn buildEklenti(String name, double price) {
        return new AddOn(name, price);
    }

}
