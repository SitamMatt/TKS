package controllers;


import model.Resource;
import services.ResourcesService;
import services.dto.ResourceDto;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("resources")
public class ResourcesController {
    @Inject
    private ResourcesService resourcesService;

    @GET
    @Produces("application/json")
    public Response getAll(){
        return Response.ok().entity(resourcesService.getAllResources()).build();
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public Response get(@PathParam("id") String id){
        UUID uuid = UUID.fromString(id);
        ResourceDto res;
        try {
            res = resourcesService.find(uuid);
        }
        catch (NullPointerException e) {
            return Response.status(404).build();
        }
        return Response.ok(res).build();
    }
}
