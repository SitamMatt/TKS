package controllers;

import services.EventsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.UUID;

@Path("events")
public class EventsController {
    @Inject
    private EventsService eventsService;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response get(@Context final Request request, @PathParam("id") String id){
        EntityTag eTag = new EntityTag("123456789");
        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(eTag);
        var uuid = UUID.fromString(id);
        var event = eventsService.find(uuid);
        if(event == null) Response.status(404).build();
        return Response.ok(event).tag(eTag).build();
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
