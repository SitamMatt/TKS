package controllers;

import exceptions.ObjectNotFoundException;
import services.EventsService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("events")
public class EventsController {
    @Inject
    private EventsService eventsService;

    @GET
    @Path("{id}")
    //todo Dodać wyswietlanie resource id i user id
//    @RolesAllowed("WORKER")
    @Produces("application/json")
    public Response get(@PathParam("id") String id){
        try {
            var uuid = UUID.fromString(id);
            var event = eventsService.find(uuid);
            return Response.ok(event).build();
        }
        catch (ObjectNotFoundException e){
            return Response.status(404, e.getMessage()).build();
        }
    }

    // todo w przypadku spa należy pomyśleć o wywyłaniu info o maks liczbie stron
    @GET
//    @RolesAllowed("WORKER")
    @Produces("application/json")
    public Response get(@QueryParam("type") String type,
                        @QueryParam("page") int page,
                        @QueryParam("maxResults") int maxResults,
                        @QueryParam("search") String search){
        var result = eventsService.filter(type, page, maxResults, search);
        return Response.ok(result).build();
    }

    // todo get user archived events + special method for user
}
