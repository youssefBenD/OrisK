/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "historique")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historique.findAll", query = "SELECT h FROM Historique h"),
    @NamedQuery(name = "Historique.findByHistoriqueId", query = "SELECT h FROM Historique h WHERE h.historiqueId = :historiqueId"),
    @NamedQuery(name = "Historique.findByImpayeId", query = "SELECT h FROM Historique h WHERE h.impayeId = :impayeId"),
    @NamedQuery(name = "Historique.findByMontant", query = "SELECT h FROM Historique h WHERE h.montant = :montant"),
    @NamedQuery(name = "Historique.findByDateAjout", query = "SELECT h FROM Historique h WHERE h.dateAjout = :dateAjout"),
    @NamedQuery(name = "Historique.findByDatePaiement", query = "SELECT h FROM Historique h WHERE h.datePaiement = :datePaiement"),
    @NamedQuery(name = "Historique.findByClientId", query = "SELECT h FROM Historique h WHERE h.clientId = :clientId")})
public class Historique implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "historique_id")
    private String historiqueId;
    @Size(max = 2147483647)
    @Column(name = "impaye_id")
    private String impayeId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant")
    private Double montant;
    @Column(name = "date_ajout")
    @Temporal(TemporalType.DATE)
    private Date dateAjout;
    @Column(name = "date_paiement")
    @Temporal(TemporalType.DATE)
    private Date datePaiement;
    @Size(max = 2147483647)
    @Column(name = "client_id")
    private String clientId;

    public Historique() {
    }

    public Historique(String historiqueId) {
        this.historiqueId = historiqueId;
    }

    public String getHistoriqueId() {
        return historiqueId;
    }

    public void setHistoriqueId(String historiqueId) {
        this.historiqueId = historiqueId;
    }

    public String getImpayeId() {
        return impayeId;
    }

    public void setImpayeId(String impayeId) {
        this.impayeId = impayeId;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historiqueId != null ? historiqueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historique)) {
            return false;
        }
        Historique other = (Historique) object;
        if ((this.historiqueId == null && other.historiqueId != null) || (this.historiqueId != null && !this.historiqueId.equals(other.historiqueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.zero.risk.Historique[ historiqueId=" + historiqueId + " ]";
    }
    
}
