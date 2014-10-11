package com.getbro.api.controller;

import com.getbro.api.ApiStorageWrapper;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;


@Path("info")
public class InfoController extends ApiStorageWrapper {
    private final static Logger Log = Logger.getLogger(InfoController.class.getName());

    public InfoController() {
    }

    /**
     * Get all events from current user
     *
     * @param request
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,String> getInfo(@Context HttpServletRequest request) {
        Map<String, String> information = new HashMap<String, String>();

        information.put("version", "v4");

        return information;
    }

}
