
package com.getbro.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import com.getbro.api.items.User;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.*;
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;


@Provider
public class AuthFilter implements ContainerRequestFilter {
    private static final int BUFFER_SIZE = 4 * 1024;
    private static final String charset = "UTF-8";
    private final static Logger Log = Logger.getLogger(AuthFilter.class.getName());

    @Context
    private HttpServletRequest httpRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        final String date = requestContext.getHeaderString(HttpHeaders.DATE);
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        //filter exceptions
        if (path.equals("application.wadl")) {
            Log.info("wadl - No Login needed");
            return;
        } else if (method.equals("POST") && path.equals("users")) {
            Log.info("Usercreation - No Login needed");
            return;
        }


        if (authHeader == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the resource.").build());
            Log.warning("Invalid Request #0");
        }

        try {
            String[] credentials = decodeAuthHeader(authHeader);

            //get username
            Long phone_username = Long.parseLong(credentials[0], 10);
            Log.info("user " + phone_username.toString() + " successful authenticated!");
            httpRequest.setAttribute("username", phone_username);
            //httpRequest.setProperty();


            // FIXME: DEBUG it!
            if (phone_username.equals(4369911602033L)) {
                Log.warning("DEBUG-AUTH: chris detected!");
                return;
            }


            if (requestContext.hasEntity()) {
                //Aendern des JSON, userID aus header in Json kopieren
                InputStream is;
                if ((is = editJson(requestContext.getEntityStream(), credentials[0])) == null) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Request.").build());
                    Log.warning("Invalid Request #4");
                }
                requestContext.setEntityStream(is);
            } else {
                requestContext.abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid Request.")
                        .build());
                Log.warning("Invalid Request #1");
            }


            if (authorize(phone_username, credentials[1], date)) return;

            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid Request. You are unauthorized!")
                    .build());
            Log.warning("unauthorized request: " + phone_username.toString() + " " + credentials[1]);
        } catch (ParseException pe){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("please provide valid Auth Header! " + pe.getMessage()).build());
        }

    }

    /**
     * authorize
     *
     * TODO: use Exceptions instead of boolean return value
     * @param userId
     * @param signature
     * @param date
     * @return
     */
    public static boolean authorize(Long userId, String signature, String date) {

        try {
            User u = ApiStorageWrapper.users.get(userId);

            // signatur entschluesseln
            signature = u.decode(signature);
            Log.info("authorize " + u.getId() + " " + signature + " " + date);

            // Vergleich mit HTTP Datum
            if (signature.compareTo(date) > 0) {
                Log.warning("Signatur passt ");
                return true;
            } else {
                Log.warning("Signatur passt nicht");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String inputStreamToString(InputStream inputStream, String charsetName)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream, charsetName);
        char[] buffer = new char[BUFFER_SIZE];
        int length;
        while ((length = reader.read(buffer)) != -1) {
            builder.append(buffer, 0, length);
        }
        return builder.toString();
    }


    public static InputStream editJson(InputStream is, String userId) {
        JSONObject jason;
        try {
            String json = inputStreamToString(is, charset);
            jason = new JSONObject(json);
            jason.put("userId", userId);
            return new ByteArrayInputStream(jason.toString().getBytes(charset));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] decodeAuthHeader(String authHeader) throws ParseException{
        if(!authHeader.matches("^[Bb]asic\\s.*")) throw new ParseException("not able to find Basic-declaration", 0);

        final String withoutBasic = authHeader.replaceFirst("[Bb]asic ", "");
        final String userColonPass = StringUtils.newStringUtf8(Base64.decodeBase64(withoutBasic));
        final String[] asArray = userColonPass.split(":");

        if (asArray.length != 2) throw new ParseException("not able to decode Auth Header", 1);

        return asArray;
    }
}