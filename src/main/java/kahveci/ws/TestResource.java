package kahveci.ws;

import kahveci.business.KahveciBean;
import kahveci.business.PurchaseBean;
import kahveci.domain.Cart;
import kahveci.domain.Eklenti;
import kahveci.domain.Kahve;
import kahveci.domain.PurchaseResult;
import kahveci.startup.DbHelper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static kahveci.business.KahveciBuilders.*;

@RequestScoped
@Path("test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @Inject
    private KahveciBean kahveciBean;

    @Inject
    private DbHelper dbHelper;

    @Inject
    private PurchaseBean purchaseBean;

    @GET
    public Response test() {
        return Response
                .ok("Merhaba Kahve Sever!")
                .build();
    }

    @GET
    @Path("purchase")
    public Response testPurchase() {
        Optional<Kahve> americano = dbHelper.findKahveByName("Americano");
        Optional<Eklenti> eklenti = dbHelper.findEklentiByEklentiAndKahveName("Sut", "Americano");
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
        PurchaseResult purchase = purchaseBean.purchase(cart);
        return Response.ok(purchase).build();
    }

}
