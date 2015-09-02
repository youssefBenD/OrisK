/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "filiale")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filiale.findAll", query = "SELECT f FROM Filiale f"),
    @NamedQuery(name = "Filiale.findByFilialeId", query = "SELECT f FROM Filiale f WHERE f.filialeId = :filialeId"),
    @NamedQuery(name = "Filiale.findByEmail", query = "SELECT f FROM Filiale f WHERE f.email = :email"),
    @NamedQuery(name = "Filiale.findByPassword", query = "SELECT f FROM Filiale f WHERE f.password = :password"),
    @NamedQuery(name = "Filiale.findByTelephone", query = "SELECT f FROM Filiale f WHERE f.telephone = :telephone"),
    @NamedQuery(name = "Filiale.findByAdresse", query = "SELECT f FROM Filiale f WHERE f.adresse = :adresse")})
public class Filiale implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "filiale")
    private Collection<Recherche> rechercheCollection;
    @JoinColumn(name = "entreprise_id", referencedColumnName = "entreprise_id")
    @ManyToOne
    @JsonBackReference
    private Entreprise entrepriseId;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "filiale_id")
    private String filialeId;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @Size(max = 2147483647)
    @Column(name = "password")
    private String password;
    @Size(max = 2147483647)
    @Column(name = "telephone")
    private String telephone;
    @Size(max = 2147483647)
    @Column(name = "adresse")
    private String adresse;

    public Filiale() {
    }

    public Filiale(String filialeId) {
        this.filialeId = filialeId;
    }

    public String getFilialeId() {
        return filialeId;
    }

    public void setFilialeId(String filialeId) {
        this.filialeId = filialeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (filialeId != null ? filialeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filiale)) {
            return false;
        }
        Filiale other = (Filiale) object;
        if ((this.filialeId == null && other.filialeId != null) || (this.filialeId != null && !this.filialeId.equals(other.filialeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.zero.risk.Filiale[ filialeId=" + filialeId + " ]";
    }

    @XmlTransient
    public Collection<Recherche> getRechercheCollection() {
        return rechercheCollection;
    }

    public void setRechercheCollection(Collection<Recherche> rechercheCollection) {
        this.rechercheCollection = rechercheCollection;
    }

    public Entreprise getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Entreprise entrepriseId) {
        this.entrepriseId = entrepriseId;
    }
    
}
