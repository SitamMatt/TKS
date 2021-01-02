package controllers;

import services.EventsService;

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
    @Produces("application/json")
    public Response get(@PathParam("id") String id){
        var uuid = UUID.fromString(id);
        var event = eventsService.find(uuid);
        if(event == null) Response.status(404).build();
        return Response.ok(event).build();
    }

    @GET
    @Produces("application/json")
    public Response get(@QueryParam("type") String type,
                        @QueryParam("page") int page,
                        @QueryParam("maxResults") int maxResults,
                        @QueryParam("search") String search){
        var result = eventsService.filter(type, page, maxResults, search);
        return Response.ok(result).build();
    }
}
