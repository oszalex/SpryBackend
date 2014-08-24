package com.getbro.api.controller;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.getbro.api.ApiStorageWrapper;
import com.getbro.api.items.User;
import org.json.*;

@Path("/users")
public class UserController extends ApiStorageWrapper {

    /**
     * get all users
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        List<User> visibleusers = new LinkedList<User>();
        return visibleusers;
    }

    /**
     * get single user by id
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userID:[0-9]+}")
    public User getUser(@PathParam("userID") String userID) {
        return users.get(Integer.parseInt(userID) - 1);
    }

    /**
     * add new user (user registration)
     */
    @POST
    //@UserAuthorization
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addJsonUser(String jason) {
        JSONObject obj = new JSONObject(jason);
        try {
            System.out.println("Creating New User " + obj.getString("publicKey"));
            User x = new User(obj.getString("publicKey"), Long.parseLong(obj.getString("userId")));
            if (!users.containsKey(x.getId())) users.put(x.getId(), x);
            x.sendConfirmation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(200).build();
    }

    @POST
    @Path("{userID:[0-9]+}")
    //@UserAuthorization
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkJsonUser(String jason) {
        try {
            JSONObject obj = new JSONObject(jason);
            int activationcode = Integer.parseInt(obj.getString("code"));
            System.out.println("Received Code" + activationcode);

            User x = users.get(Long.parseLong(obj.getString("userId")));
            if (x == null) return Response.status(500).entity(x).build();
            if (x.checkActivation(activationcode)) return Response.status(200).entity(x).build();
            else return Response.status(501).entity(x).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(500).build();
        //No such user exception
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addUsertoEvent")
    public User addUsertoEvent(String jason) {
        User x = users.get(1);
        //events.get(Integer.parseInt(eventID)-1).addUser(x);
        return x;
    }

}
 

