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
import tn.orisk.SiegeSocial;

/**
 *
 * @author user
 */
@Stateless
@Path("siegesocial")
public class SiegeSocialFacadeREST extends AbstractFacade<SiegeSocial> {
    @PersistenceContext(unitName = "0riskPU")
    private EntityManager em;

    public SiegeSocialFacadeREST() {
        super(SiegeSocial.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(SiegeSocial entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/json"})
    public void edit(SiegeSocial entity) {
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
    public SiegeSocial find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Path("findAll")
    @Override
    @Produces({"application/json"})
    public List<SiegeSocial> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<SiegeSocial> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
