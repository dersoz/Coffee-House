package kahveci.ws;

import kahveci.business.KahveciBean;
import kahveci.business.PurchaseBean;
import kahveci.domain.Cart;
import kahveci.domain.Kahve;
import kahveci.domain.PurchaseResult;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
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

    @Inject
    private KahveciBean kahveciBean;

    @PUT
    public Response purchase(Cart cart, @Context UriInfo uriInfo) {
        PurchaseResult purchaseResult = purchaseBean.purchase(cart);
        return Response.ok(purchaseResult).build();
    }

    @GET
    @Path("sales/{id}")
    public Response getSalesCount(@PathParam("id") long kahveID) {
        Kahve kahve = new Kahve();
        kahve.setId(kahveID);
        int res = purchaseBean.getCountForKahve(kahve);
        return Response.ok(res).build();
    }

}
