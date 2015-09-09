/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.ws.rs.core.Response;
import tn.orisk.Impaye;
import tn.orisk.Recherche;

/**
 *
 * @author user
 */
@Stateless
@Path("impaye")
public class ImpayeFacadeREST extends AbstractFacade<Impaye> {

    @PersistenceContext(unitName = "0riskPU")
    private EntityManager em;

    public ImpayeFacadeREST() {
        super(Impaye.class);
    }

    @POST
    @Path("ajout")
    @Consumes({"application/json"})
    @Produces("text/plain")
    public String ajouter(Object o) {
        HashMap<String, String> elmt = (HashMap<String, String>) o;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        //Vérifier le code de l'utilisateur
        Query queryCodeUser = em.createNativeQuery("SELECT code FROM filiale WHERE filiale_id='" + elmt.get("filialeId") + "'");
        if (queryCodeUser.getSingleResult().toString().equals(elmt.get("code"))) {
            //Vérifier si la filiale a dépassé la somme max Impayes
            Query querySommeImpayes = em.createNativeQuery("SELECT SUM(montant) FROM impaye WHERE filiale_id='" + elmt.get("filialeId") + "'");
            Query queryMaxImpayes = em.createNativeQuery("SELECT max_impaye FROM pack p, abonnement a, entreprise e WHERE p.pack_id=a.pack_id AND e.entreprise_id=a.entreprise_id AND e.entreprise_id='" + elmt.get("entrepriseId") + "'");
            Double sommeImpayes = (Double) querySommeImpayes.getSingleResult();
            Double maxImpayes = (Double) queryMaxImpayes.getSingleResult();
            System.out.println(String.valueOf(sommeImpayes));
            System.out.println(String.valueOf(maxImpayes));
            if (sommeImpayes <= maxImpayes) {
                //Vérifier si le client existe dans la table Client sinon l'ajouter
                Query queryClient = em.createNativeQuery("SELECT count(*) FROM client WHERE client_id='" + elmt.get("clientId") + "'");
                if (queryClient.getSingleResult().toString().equals("0")) {
                    Query insertClient = em.createNativeQuery("INSERT INTO client (client_id, type)\n"
                            + "VALUES ('" + elmt.get("clientId") + "','" + elmt.get("typeClient") + "')");
                    insertClient.executeUpdate();
                }
                //Get Serial ID From Recherche
                Query querySerial = em.createNativeQuery("SELECT MAX(serial) FROM impaye ");
                Query insert = em.createNativeQuery("INSERT INTO impaye (impaye_id, montant, date_ajout, date_paiement, client_id, date_echeance, type, filiale_id)\n"
                        + "VALUES ('" + querySerial.getSingleResult().toString() + "','" + elmt.get("montant") + "','" + dateFormat.format(date) + "','" + elmt.get("datePaiement") + "','" + elmt.get("clientId") + "','" + elmt.get("dateEcheance") + "','" + elmt.get("type") + "','" + elmt.get("filialeId") + "')");
                insert.executeUpdate();
                System.out.println("Succes Operation");
                return "Succes Operation";
            } else {
                return "Max Impayes depasse";
            }



        } else {
            System.out.println("Code Incorrect");
            return "Code Incorrect";
        }

    }

    @PUT
    @Path("evaluerRisque")
    @Consumes({"application/json"})
    @Produces("text/plain")
    public String evaluerRisque(Object o) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        HashMap<String, String> elmt = (HashMap<String, String>) o;
        Query q = em.createNativeQuery("SELECT count(*) FROM impaye i, filiale f, entreprise e, abonnement a WHERE i.filiale_id=f.filiale_id AND f.entreprise_id=e.entreprise_id \n"
                + "AND a.entreprise_id = e.entreprise_id AND a.date_expiration>current_date AND i.client_id='" + elmt.get("clientId") + "'");
        //Get Serial ID From Recherche
        Query querySerial = em.createNativeQuery("SELECT MAX(serial) FROM recherche ");
        //Insert Row Recherche
        Query insert = em.createNativeQuery("INSERT INTO recherche (recherche_id, filiale_id, client_id, date, cle_recherche)\n"
                + "VALUES ('" + querySerial.getSingleResult().toString() + "','" + elmt.get("filialeId") + "','" + elmt.get("clientId") + "','" + dateFormat.format(date) + "','cle')");
        insert.executeUpdate();

        return String.valueOf(q.getResultList().get(0));
    }

    @PUT
    @Path("ajoutInfo")
    @Consumes({"application/json"})
    @Produces("application/json")
    public List ajoutInfo(Object o) {
        ArrayList al = (ArrayList) o;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < al.size(); i++) {
            HashMap<String, String> elmt = (HashMap<String, String>) al.get(i);
            if (dateFormat.format(elmt.get("datePaiement")).toString().equals("2001-01-01")) {
                elmt.put("clientId", super.find(elmt.get("impayeId")).getClientId().getClientId());
                elmt.put("typeClient", super.find(elmt.get("impayeId")).getClientId().getType());
            } else {
                al.remove(elmt);
            }
        }
        return al;
    }

    @PUT
    @Override
    @Consumes({"application/json"})
    public void edit(Impaye entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        System.out.println(id);
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Impaye find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Path("findAll")
    @Override
    @Produces({"application/json"})
    public List<Impaye> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Impaye> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
