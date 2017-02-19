package com.hascode.tutorial;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/user")
public class UserResource {
    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private static final Map<String, User> fakeStore = new ConcurrentHashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Collection<User> users = fakeStore.values();
        return Response.ok(new GenericEntity<Collection<User>>(users) {
        }).build();
    }

    @Path("/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("userId") String userId) {
        return Response.ok(fakeStore.get(userId)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(User user) {
        user.setId(UUID.randomUUID().toString().toUpperCase());
        fakeStore.put(user.getId(), user);

        LOG.info("new user {} saved", user);
        return Response.ok(user).build();
    }

    @DELETE
    public Response purgeUsers() {
        fakeStore.clear();
        return Response.ok().build();
    }
}
