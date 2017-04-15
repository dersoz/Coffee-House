package kahveci.ws;

import kahveci.domain.Eklenti;
import kahveci.business.KahveciBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@RequestScoped
@Path("/eklenti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EklentiResource {

    @Inject
    private KahveciBean kahveciBean;

    @GET
    public Response getAllEklenti() {
        return Response.ok()
                .entity(kahveciBean.getAllEklenti())
                .build();
    }

    @POST
    public Response addEklenti(Eklenti eklenti, @Context UriInfo uriInfo) {
        kahveciBean.addEklentiler(eklenti);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(eklenti.getId()));
        return Response.created(uriBuilder.build()).build();
    }

}
