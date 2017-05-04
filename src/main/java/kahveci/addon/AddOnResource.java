package kahveci.addon;

import kahveci.shared.WsPaths;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@RequestScoped
@Path(WsPaths.ADDON_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddOnResource {

    @Inject
    private AddOnCrud addOnCrud;

    @GET
    public Response getAllAddOn() {
        return Response.ok()
                .entity(addOnCrud.findAllAddOn())
                .build();
    }

    @POST
    public Response addAddOn(AddOn addOn, @Context UriInfo uriInfo) {
        addOnCrud.addAddOns(addOn);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(addOn.getId()));
        return Response.created(uriBuilder.build()).build();
    }

}
