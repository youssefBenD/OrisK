/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author user
 */
@Entity
@Table(name = "impaye")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Impaye.findAll", query = "SELECT i FROM Impaye i"),
    @NamedQuery(name = "Impaye.findByImpayeId", query = "SELECT i FROM Impaye i WHERE i.impayeId = :impayeId"),
    @NamedQuery(name = "Impaye.findByMontant", query = "SELECT i FROM Impaye i WHERE i.montant = :montant"),
    @NamedQuery(name = "Impaye.findByDateAjout", query = "SELECT i FROM Impaye i WHERE i.dateAjout = :dateAjout"),
    @NamedQuery(name = "Impaye.findByDatePaiement", query = "SELECT i FROM Impaye i WHERE i.datePaiement = :datePaiement")})
public class Impaye implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "sequence",
            sequenceName = "sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence")
    @Basic(optional = false)
    @Size(min = 1, max = 2147483647)
    @Column(name = "impaye_id")
    private String impayeId;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant")
    private Double montant;
    
    @Column(name = "date_ajout")
    @Temporal(TemporalType.DATE)
    private Date dateAjout;
    
    @Column(name = "date_echeance")
    @Temporal(TemporalType.DATE)
    private Date dateEcheance;
    
    @Column(name = "date_paiement")
    @Temporal(TemporalType.DATE)
    private Date datePaiement;
    
    @Size(max = 2147483647)
    @Column(name = "type")
    private String type;
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @JsonBackReference
    private Client clientId;
    
    @JoinColumn(name = "filiale_id", referencedColumnName = "filiale_id")
    @ManyToOne
    @JsonBackReference
    private Filiale filialeId;
    

    public Impaye() {
    }

    public Impaye(String impayeId) {
        this.impayeId = impayeId;
    }

    public String getImpayeId() {
        return impayeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
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

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (impayeId != null ? impayeId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Impaye{" + "impayeId=" + impayeId + ", type=" + type + ", montant=" + montant + ", dateAjout=" + dateAjout + ", dateEcheance=" + dateEcheance + ", datePaiement=" + datePaiement + ", clientId=" + clientId + '}';
    }


    public Filiale getFilialeId() {
        return filialeId;
    }

    public void setFilialeId(Filiale filialeId) {
        this.filialeId = filialeId;
    }

    


    
}
