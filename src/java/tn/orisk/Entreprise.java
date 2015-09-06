/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "entreprise")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entreprise.findAll", query = "SELECT e FROM Entreprise e"),
    @NamedQuery(name = "Entreprise.findByEntrepriseId", query = "SELECT e FROM Entreprise e WHERE e.entrepriseId = :entrepriseId"),
    @NamedQuery(name = "Entreprise.findByMatriculeFiscale", query = "SELECT e FROM Entreprise e WHERE e.matriculeFiscale = :matriculeFiscale"),
    @NamedQuery(name = "Entreprise.findByRaisonSociale", query = "SELECT e FROM Entreprise e WHERE e.raisonSociale = :raisonSociale")})
public class Entreprise implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "entreprise_id")
    private String entrepriseId;
    
    @Size(max = 2147483647)
    @Column(name = "matricule_fiscale")
    private String matriculeFiscale;
    
    @Size(max = 2147483647)
    @Column(name = "raison_sociale")
    private String raisonSociale;
    
    @OneToOne(mappedBy = "entrepriseId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private SiegeSocial siegeSocial;
    
    @OneToMany(mappedBy = "entrepriseId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<Filiale> filialeCollection;
    
    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<Abonnement> abonnementCollection;

    public Entreprise() {
    }

    public Entreprise(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public String getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entrepriseId != null ? entrepriseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entreprise)) {
            return false;
        }
        Entreprise other = (Entreprise) object;
        if ((this.entrepriseId == null && other.entrepriseId != null) || (this.entrepriseId != null && !this.entrepriseId.equals(other.entrepriseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.zero.risk.Entreprise[ entrepriseId=" + entrepriseId + " ]";
    }

    @XmlTransient
    public Collection<Abonnement> getAbonnementCollection() {
        return abonnementCollection;
    }

    public void setAbonnementCollection(Collection<Abonnement> abonnementCollection) {
        this.abonnementCollection = abonnementCollection;
    }

    public SiegeSocial getSiegeSocialCollection() {
        return siegeSocial;
    }

    public void setSiegeSocialCollection(SiegeSocial siegeSocialCollection) {
        this.siegeSocial = siegeSocialCollection;
    }

    @XmlTransient
    public Collection<Filiale> getFilialeCollection() {
        return filialeCollection;
    }

    public void setFilialeCollection(Collection<Filiale> filialeCollection) {
        this.filialeCollection = filialeCollection;
    }
    
}
