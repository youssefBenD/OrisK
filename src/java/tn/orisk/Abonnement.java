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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "abonnement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Abonnement.findAll", query = "SELECT a FROM Abonnement a"),
    @NamedQuery(name = "Abonnement.findByPackId", query = "SELECT a FROM Abonnement a WHERE a.abonnementPK.packId = :packId"),
    @NamedQuery(name = "Abonnement.findByEntrepriseId", query = "SELECT a FROM Abonnement a WHERE a.abonnementPK.entrepriseId = :entrepriseId"),
    @NamedQuery(name = "Abonnement.findByDateExpiration", query = "SELECT a FROM Abonnement a WHERE a.dateExpiration = :dateExpiration"),
    @NamedQuery(name = "Abonnement.findByDatePaiement", query = "SELECT a FROM Abonnement a WHERE a.datePaiement = :datePaiement"),
    @NamedQuery(name = "Abonnement.findByPrixHt", query = "SELECT a FROM Abonnement a WHERE a.prixHt = :prixHt")})
public class Abonnement implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AbonnementPK abonnementPK;
    
    @Column(name = "date_expiration")
    @Temporal(TemporalType.DATE)
    private Date dateExpiration;
    
    @Column(name = "date_paiement")
    @Temporal(TemporalType.DATE)
    private Date datePaiement;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prix_ht")
    private Double prixHt;
    
    @JoinColumn(name = "pack_id", referencedColumnName = "pack_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonBackReference
    private Pack pack;
    
    @JoinColumn(name = "entreprise_id", referencedColumnName = "entreprise_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonBackReference
    private Entreprise entreprise;

    public Abonnement() {
    }

    public Abonnement(AbonnementPK abonnementPK) {
        this.abonnementPK = abonnementPK;
    }

    public Abonnement(String packId, String entrepriseId) {
        this.abonnementPK = new AbonnementPK(packId, entrepriseId);
    }

    public AbonnementPK getAbonnementPK() {
        return abonnementPK;
    }

    public void setAbonnementPK(AbonnementPK abonnementPK) {
        this.abonnementPK = abonnementPK;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getPrixHt() {
        return prixHt;
    }

    public void setPrixHt(Double prixHt) {
        this.prixHt = prixHt;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (abonnementPK != null ? abonnementPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abonnement)) {
            return false;
        }
        Abonnement other = (Abonnement) object;
        if ((this.abonnementPK == null && other.abonnementPK != null) || (this.abonnementPK != null && !this.abonnementPK.equals(other.abonnementPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.orisk.Abonnement[ abonnementPK=" + abonnementPK + " ]";
    }
    
}
