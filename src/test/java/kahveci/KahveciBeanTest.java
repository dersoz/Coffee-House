package kahveci;

import kahveci.business.KahveciBean;
import kahveci.domain.Eklenti;
import kahveci.domain.Kahve;
import kahveci.shared.ArquillianDeployer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(Arquillian.class)
public class KahveciBeanTest {

    @Inject
    private KahveciBean kahveciBean;

    @Deployment
    public static WebArchive createDeployment() {
        return ArquillianDeployer.createDeployment();
    }

    @Test
    public void shouldAddEklentiAndKahve() {
        Set<Eklenti> eklentiler = getEklentiler();

        Assert.assertTrue(kahveciBean.getAllKahve().isEmpty());
        Kahve k1 = buildKahve("Turk Kahvesi", 5);
        k1.setApplicableAddons(eklentiler);
        kahveciBean.addKahve(k1);
        Assert.assertFalse(kahveciBean.getAllKahve().isEmpty());
        Assert.assertEquals(Long.valueOf(1), kahveciBean.getAllKahveCount());
    }

    @NotNull
    private Set<Eklenti> getEklentiler() {
        Assert.assertTrue(kahveciBean.getAllEklenti().isEmpty());
        Eklenti e1 = buildEklenti("Limon", 1);
        Eklenti e2 = buildEklenti("Tarcin", 0.5);
        Set<Eklenti> eklentiler = new HashSet<>(Arrays.asList(e1, e2));
        kahveciBean.addEklentiler(e1, e2);
        Assert.assertFalse(kahveciBean.getAllEklenti().isEmpty());
        Assert.assertEquals(2, kahveciBean.getAllEklenti().size());
        return eklentiler;
    }

    private Kahve buildKahve(String name, double price) {
        return Kahve.builder()
                .name(name)
                .price(price)
                .build();
    }

    private Eklenti buildEklenti(String name, double price) {
        return Eklenti.builder()
                .name(name)
                .price(price)
                .build();
    }

}
