package kahveci.coffee;

import kahveci.shared.WsPaths;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@RequestScoped
@Path(WsPaths.COFFEE_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CoffeeResource {

    @Inject
    private CoffeeCrud coffeeCrud;

    @GET
    public Response getAllCoffee() {
        return Response
                .ok()
                .entity(coffeeCrud.getAllCoffeeEager())
                .build();
    }

    @POST
    public Response addCoffee(Coffee coffee, @Context UriInfo uriInfo) {
        coffeeCrud.addKahve(coffee);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(coffee.getId()));
        return Response.created(uriBuilder.build()).build();
    }

}
