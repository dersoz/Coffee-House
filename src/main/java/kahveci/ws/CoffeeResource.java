package kahveci.ws;

import kahveci.business.coffee.CoffeeCrud;
import kahveci.domain.Coffee;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@RequestScoped
@Path("/kahve")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CoffeeResource {

    @Inject
    private CoffeeCrud coffeeCrud;

    @GET
    public Response getAllKahve() {
        return Response
                .ok()
                .entity(coffeeCrud.getAllKahveEager())
                .build();
    }

    @POST
    public Response addKahve(Coffee coffee, @Context UriInfo uriInfo) {
        coffeeCrud.addKahve(coffee);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(coffee.getId()));
        return Response.created(uriBuilder.build()).build();
    }

}
