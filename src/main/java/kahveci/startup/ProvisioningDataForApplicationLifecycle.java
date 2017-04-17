package kahveci.startup;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ProvisioningDataForApplicationLifecycle {

    @Inject
    DbHelper dbHelper;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        System.out.println("Provisioning is called upon ApplicationScoped class");
        dbHelper.addInitData();
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
    }

}
