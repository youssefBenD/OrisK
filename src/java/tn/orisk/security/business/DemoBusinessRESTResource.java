/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk.security.business;

import tn.orisk.security.intf.DemoHTTPHeaderNames;
import java.security.GeneralSecurityException;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Stateless( name = "DemoBusinessRESTResource", mappedName = "ejb/DemoBusinessRESTResource" )
@Path( "security" )
public class DemoBusinessRESTResource {
 
    private static final long serialVersionUID = -6663599014192066936L;
 
    @POST
    @Path( "login" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response login(
        @Context HttpHeaders httpHeaders,
        @FormParam( "username" ) String username,
        @FormParam( "password" ) String password ) {
       
        DemoAuthenticator demoAuthenticator = DemoAuthenticator.getInstance();
        String serviceKey = httpHeaders.getHeaderString( DemoHTTPHeaderNames.SERVICE_KEY );
 
        try {
            String authToken = demoAuthenticator.login( serviceKey, username, password );
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "auth_token", authToken );
            JsonObject jsonObj = jsonObjBuilder.build();
 
            return getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();
 
        } catch ( final LoginException ex ) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", "Problem matching service key, username and password" );
            JsonObject jsonObj = jsonObjBuilder.build();
 
            return getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        }
    }
 
    @GET
    @Path( "demo-get-method" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response demoGetMethod() {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add( "message", "Executed demoGetMethod" );
        JsonObject jsonObj = jsonObjBuilder.build();
 
        return getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();
    }
 
    @POST
    @Path( "demo-post-method" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response demoPostMethod() {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add( "message", "Executed demoPostMethod" );
        JsonObject jsonObj = jsonObjBuilder.build();
 
        return getNoCacheResponseBuilder( Response.Status.ACCEPTED ).entity( jsonObj.toString() ).build();
    }
 
    @POST
    @Path( "logout" )
    public Response logout(
        @Context HttpHeaders httpHeaders ) {
        try {
            DemoAuthenticator demoAuthenticator = DemoAuthenticator.getInstance();
            String serviceKey = httpHeaders.getHeaderString( DemoHTTPHeaderNames.SERVICE_KEY );
            String authToken = httpHeaders.getHeaderString( DemoHTTPHeaderNames.AUTH_TOKEN );
 
            demoAuthenticator.logout( serviceKey, authToken );
 
            return getNoCacheResponseBuilder( Response.Status.NO_CONTENT ).build();
        } catch ( final GeneralSecurityException ex ) {
            return getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).build();
        }
    }
 
    private Response.ResponseBuilder getNoCacheResponseBuilder( Response.Status status ) {
        CacheControl cc = new CacheControl();
        cc.setNoCache( true );
        cc.setMaxAge( -1 );
        cc.setMustRevalidate( true );
 
        return Response.status( status ).cacheControl( cc );
    }
}