/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.PathSegment;
import tn.orisk.Recherche;
import tn.orisk.RecherchePK;

/**
 *
 * @author user
 */
@Stateless
@Path("recherche")
public class RechercheFacadeREST extends AbstractFacade<Recherche> {
    @PersistenceContext(unitName = "0riskPU")
    private EntityManager em;

    private RecherchePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;clientId=clientIdValue;filialeId=filialeIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        tn.orisk.RecherchePK key = new tn.orisk.RecherchePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> clientId = map.get("clientId");
        if (clientId != null && !clientId.isEmpty()) {
            key.setClientId(clientId.get(0));
        }
        java.util.List<String> filialeId = map.get("filialeId");
        if (filialeId != null && !filialeId.isEmpty()) {
            key.setFilialeId(filialeId.get(0));
        }
        return key;
    }

    public RechercheFacadeREST() {
        super(Recherche.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Recherche entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/json"})
    public void edit(Recherche entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        tn.orisk.RecherchePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Recherche find(@PathParam("id") PathSegment id) {
        tn.orisk.RecherchePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Recherche> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Recherche> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
