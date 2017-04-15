package kahveci;

import kahveci.shared.DbHelper;
import kahveci.shared.Eager;
import kahveci.business.KahveciBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@Eager
@Named
public class StartUpController {

    @Inject
    private KahveciBean kahveciBean;

    @Inject
    private DbHelper dbHelper;

    @PostConstruct
    public void init() {
    }

}