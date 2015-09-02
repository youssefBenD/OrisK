/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "pack")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pack.findAll", query = "SELECT p FROM Pack p"),
    @NamedQuery(name = "Pack.findByPackId", query = "SELECT p FROM Pack p WHERE p.packId = :packId"),
    @NamedQuery(name = "Pack.findByLibelle", query = "SELECT p FROM Pack p WHERE p.libelle = :libelle"),
    @NamedQuery(name = "Pack.findByMaxFiliales", query = "SELECT p FROM Pack p WHERE p.maxFiliales = :maxFiliales"),
    @NamedQuery(name = "Pack.findByPrixHt", query = "SELECT p FROM Pack p WHERE p.prixHt = :prixHt"),
    @NamedQuery(name = "Pack.findByMaxImpaye", query = "SELECT p FROM Pack p WHERE p.maxImpaye = :maxImpaye")})
public class Pack implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pack")
    private Collection<Abonnement> abonnementCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "pack_id")
    private String packId;
    @Size(max = 2147483647)
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "max_filiales")
    private Integer maxFiliales;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prix_ht")
    private Double prixHt;
    @Column(name = "max_impaye")
    private Double maxImpaye;

    public Pack() {
    }

    public Pack(String packId) {
        this.packId = packId;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getMaxFiliales() {
        return maxFiliales;
    }

    public void setMaxFiliales(Integer maxFiliales) {
        this.maxFiliales = maxFiliales;
    }

    public Double getPrixHt() {
        return prixHt;
    }

    public void setPrixHt(Double prixHt) {
        this.prixHt = prixHt;
    }

    public Double getMaxImpaye() {
        return maxImpaye;
    }

    public void setMaxImpaye(Double maxImpaye) {
        this.maxImpaye = maxImpaye;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (packId != null ? packId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pack)) {
            return false;
        }
        Pack other = (Pack) object;
        if ((this.packId == null && other.packId != null) || (this.packId != null && !this.packId.equals(other.packId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.zero.risk.Pack[ packId=" + packId + " ]";
    }

    @XmlTransient
    public Collection<Abonnement> getAbonnementCollection() {
        return abonnementCollection;
    }

    public void setAbonnementCollection(Collection<Abonnement> abonnementCollection) {
        this.abonnementCollection = abonnementCollection;
    }
    
}
