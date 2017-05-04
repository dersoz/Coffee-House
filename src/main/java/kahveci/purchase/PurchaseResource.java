package kahveci.purchase;

import kahveci.coffee.Coffee;
import kahveci.purchase.domain.Cart;
import kahveci.purchase.domain.PurchaseResult;
import kahveci.shared.WsPaths;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Path(WsPaths.PURCHASE_PATH)
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

    @GET
    @Path("sales")
    public Response getSalesCount() {
        List<SaleStat> saleStats = purchaseBean
                .getAllPurchase()
                .stream()
                .map(p -> SaleStat.of(p.getCoffee().getName(), p.getSalesCount()))
                .collect(Collectors.toList());
        return Response.ok(saleStats).build();
    }

    @GET
    @Path("sales/{coffeeID}")
    public Response getSalesCount(@PathParam("coffeeID") long kahveID) {
        Coffee coffee = new Coffee();
        coffee.setId(kahveID);
        int res = purchaseBean.getCountForKahve(coffee);
        return Response.ok(res).build();
    }

    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @Getter
    @Setter
    private static class SaleStat {
        private String kahve;
        private int satisAdedi;
    }

}
