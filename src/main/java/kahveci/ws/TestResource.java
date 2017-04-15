package kahveci.ws;

import kahveci.business.KahveciBean;
import kahveci.domain.Eklenti;
import kahveci.domain.Kahve;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static kahveci.business.KahveciBuilders.buildEklenti;

@RequestScoped
@Path("test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @Inject
    private KahveciBean kahveciBean;

    @GET
    public Response test() {
        return Response.ok("Hello World!").build();
    }

    @GET
    @Path("init")
    public Response init() {
        Eklenti e1 = buildEklenti("Sut", 1);
        Eklenti e2 = buildEklenti("Su", 0.5);
        Eklenti e3 = buildEklenti("Findik Surubu", 1.5);
        Eklenti e4 = buildEklenti("Tarcin", 2);
        kahveciBean.addEklentiler(e1, e2, e3, e4);
        Kahve k1 = Kahve.builder().name("Americano").price(5).applicableAddons(new HashSet<>(Arrays.asList(e1, e2, e3))).build();
        Kahve k2 = Kahve.builder().name("Turkish").price(3).build();
        Kahve k3 = Kahve.builder().name("Espresso").price(5).applicableAddons(new HashSet<>(Arrays.asList(e1, e3))).build();
        Kahve k4 = Kahve.builder().name("Latte").price(5).applicableAddons(new HashSet<>(Collections.singletonList(e3))).build();
        Kahve k5 = Kahve.builder().name("Cay").price(5).applicableAddons(new HashSet<>(Arrays.asList(e2, e4))).build();
        kahveciBean.addKahveler(k1, k2, k3, k4, k5);
        return Response.ok("Kahveler Eklendi").build();
    }

}
