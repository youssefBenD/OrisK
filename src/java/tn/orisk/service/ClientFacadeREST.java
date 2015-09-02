/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
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
import tn.orisk.Client;
import tn.orisk.Impaye;

/**
 *
 * @author user
 */
@Stateless
@Path("client")
public class ClientFacadeREST extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "0riskPU")
    private EntityManager em;

    public ClientFacadeREST() {
        super(Client.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Client entity) {
        super.create(entity);
    }
       
           
    @POST
    @Path("ajouterImpaye")
    @Consumes({"application/json"})
    public void ajouterImpaye(Impaye entity) {
        Client client = super.find(entity.getClientId().getClientId());
        if (client != null) {
            client.getImpayeCollection().add(entity);
            em.persist(client);
        } else {
            Client nvClient = new Client(entity.getClientId().getClientId());
            Collection<Impaye> impayeCollection = new ArrayList<Impaye>();
            impayeCollection.add(entity);
            nvClient.setImpayeCollection(impayeCollection);
            em.persist(nvClient);
        }
    }

    @PUT
    @Override
    @Consumes({"application/json"})
    public void edit(Client entity) {
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
    public Client find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Path("findAll")
    @Override
    @Produces({"application/json"})
    public List<Client> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Client> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
