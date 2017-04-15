package kahveci.ws;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("kahveci")
public class KahveciJaxRsConfigurator extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(EklentiResource.class);
        classSet.add(KahveResource.class);
        classSet.add(PurchaseResource.class);
        return classSet;
    }

}
