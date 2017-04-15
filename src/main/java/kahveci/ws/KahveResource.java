package kahveci.ws;

import kahveci.domain.Kahve;
import kahveci.business.KahveciBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@RequestScoped
@Path("/kahve")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KahveResource {

    @Inject
    private KahveciBean kahveciBean;

    @GET
    public Response getAllKahve() {
        return Response.ok()
                .entity(kahveciBean.getAllKahve())
                .build();
    }

    @POST
    public Response addKahve(Kahve kahve, @Context UriInfo uriInfo) {
        kahveciBean.addKahve(kahve);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(kahve.getId()));
        return Response.created(uriBuilder.build()).build();
    }

}
