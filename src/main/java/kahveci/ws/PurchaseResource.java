package kahveci.ws;

import kahveci.domain.Cart;
import kahveci.domain.PurchaseResult;
import kahveci.business.PurchaseBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@RequestScoped
@Path("purchase")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PurchaseResource {

    @Inject
    private PurchaseBean purchaseBean;

    @PUT
    public Response purchase(Cart cart, @Context UriInfo uriInfo) {
        PurchaseResult purchaseResult = purchaseBean.purchase(cart);
        return Response.ok(purchaseResult).build();
    }

}
