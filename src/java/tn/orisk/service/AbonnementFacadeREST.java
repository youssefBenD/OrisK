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
import tn.orisk.Abonnement;
import tn.orisk.AbonnementPK;

/**
 *
 * @author user
 */
@Stateless
@Path("abonnement")
public class AbonnementFacadeREST extends AbstractFacade<Abonnement> {
    @PersistenceContext(unitName = "0riskPU")
    private EntityManager em;

    private AbonnementPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;packId=packIdValue;entrepriseId=entrepriseIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        tn.orisk.AbonnementPK key = new tn.orisk.AbonnementPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> packId = map.get("packId");
        if (packId != null && !packId.isEmpty()) {
            key.setPackId(packId.get(0));
        }
        java.util.List<String> entrepriseId = map.get("entrepriseId");
        if (entrepriseId != null && !entrepriseId.isEmpty()) {
            key.setEntrepriseId(entrepriseId.get(0));
        }
        return key;
    }

    public AbonnementFacadeREST() {
        super(Abonnement.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Abonnement entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/json"})
    public void edit(Abonnement entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        tn.orisk.AbonnementPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Abonnement find(@PathParam("id") PathSegment id) {
        tn.orisk.AbonnementPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Path("findAll")
    @Override
    @Produces({"application/json"})
    public List<Abonnement> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Abonnement> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
