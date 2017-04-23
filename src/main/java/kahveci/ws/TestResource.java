package kahveci.ws;

import kahveci.business.purchase.PurchaseBean;
import kahveci.domain.*;
import kahveci.startup.DbHelper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Optional;

@RequestScoped
@Path(WsPaths.TEST_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @Inject
    private DbHelper dbHelper;

    @Inject
    private PurchaseBean purchaseBean;

    @GET
    public Response test() {
        return Response
                .ok("Hello Coffee Lovers!")
                .build();
    }

    @GET
    @Path("purchase")
    public Response testPurchase() {
        Optional<Coffee> americano = dbHelper.findCoffeeByName("Americano");
        Optional<AddOn> eklenti = dbHelper.findAddOnByNameAndByCoffeeName("Sut", "Americano");
        if (!americano.isPresent() || !eklenti.isPresent())
            return Response.status(Response.Status.BAD_REQUEST).build();
        Cart cart = new Cart(
                Collections.singletonList(
                        new PurchaseItem(americano.get(), Collections.singleton(eklenti.get()))
                )
        );
        PurchaseResult purchase = purchaseBean.purchase(cart);
        return Response.ok(purchase).build();
    }

}
