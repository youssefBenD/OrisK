/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "recherche")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recherche.findAll", query = "SELECT r FROM Recherche r"),
    @NamedQuery(name = "Recherche.findByClientId", query = "SELECT r FROM Recherche r WHERE r.recherchePK.clientId = :clientId"),
    @NamedQuery(name = "Recherche.findByFilialeId", query = "SELECT r FROM Recherche r WHERE r.recherchePK.filialeId = :filialeId"),
    @NamedQuery(name = "Recherche.findByDate", query = "SELECT r FROM Recherche r WHERE r.date = :date"),
    @NamedQuery(name = "Recherche.findByCleRecherche", query = "SELECT r FROM Recherche r WHERE r.cleRecherche = :cleRecherche")})
public class Recherche implements Serializable {
   
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecherchePK recherchePK;
    
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Size(max = 2147483647)
    @Column(name = "cle_recherche")
    private String cleRecherche;
    
    @JoinColumn(name = "filiale_id", referencedColumnName = "filiale_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonBackReference
    private Filiale filiale;
    
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonBackReference
    private Client client;

    public Recherche() {
    }

    public Recherche(RecherchePK recherchePK) {
        this.recherchePK = recherchePK;
    }

    public Recherche(String clientId, String filialeId) {
        this.recherchePK = new RecherchePK(clientId, filialeId);
    }

    public RecherchePK getRecherchePK() {
        return recherchePK;
    }

    public void setRecherchePK(RecherchePK recherchePK) {
        this.recherchePK = recherchePK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCleRecherche() {
        return cleRecherche;
    }

    public void setCleRecherche(String cleRecherche) {
        this.cleRecherche = cleRecherche;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recherchePK != null ? recherchePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recherche)) {
            return false;
        }
        Recherche other = (Recherche) object;
        if ((this.recherchePK == null && other.recherchePK != null) || (this.recherchePK != null && !this.recherchePK.equals(other.recherchePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.zero.risk.Recherche[ recherchePK=" + recherchePK + " ]";
    }

    public Filiale getFiliale() {
        return filiale;
    }

    public void setFiliale(Filiale filiale) {
        this.filiale = filiale;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
}
