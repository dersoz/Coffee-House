package kahveci.ws;

import kahveci.business.eklenti.AddOnCrud;
import kahveci.domain.AddOn;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@RequestScoped
@Path("/eklenti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddOnResource {

    @Inject
    private AddOnCrud addOnCrud;

    @GET
    public Response getAllEklenti() {
        return Response.ok()
                .entity(addOnCrud.getAllEklenti())
                .build();
    }

    @POST
    public Response addEklenti(AddOn addOn, @Context UriInfo uriInfo) {
        addOnCrud.addEklentiler(addOn);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(addOn.getId()));
        return Response.created(uriBuilder.build()).build();
    }

}
