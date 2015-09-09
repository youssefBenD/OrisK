/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk.service;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import tn.orisk.Historique;

/**
 *
 * @author user
 */
@Stateless
@Path("historique")
public class HistoriqueFacadeREST extends AbstractFacade<Historique> {
    @PersistenceContext(unitName = "0riskPU")
    private EntityManager em;

    public HistoriqueFacadeREST() {
        super(Historique.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Historique entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/json"})
    public void edit(Historique entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Historique find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @PUT
    @Path("listeHistorique")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public List listeHistorique(Object o) {
        HashMap<String, String> elmt = (HashMap<String, String>)o;
        Query q = em.createNativeQuery("SELECT * FROM historique WHERE client_id='"+elmt.get("clientId")+"' AND filiale_id='"+elmt.get("filialeId")+"'");
        return q.getResultList();
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Historique> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Historique> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
