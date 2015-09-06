/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "siege_social")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SiegeSocial.findAll", query = "SELECT s FROM SiegeSocial s"),
    @NamedQuery(name = "SiegeSocial.findByFilialeId", query = "SELECT s FROM SiegeSocial s WHERE s.filialeId = :filialeId"),
    @NamedQuery(name = "SiegeSocial.findByEmail", query = "SELECT s FROM SiegeSocial s WHERE s.email = :email"),
    @NamedQuery(name = "SiegeSocial.findByPassword", query = "SELECT s FROM SiegeSocial s WHERE s.password = :password"),
    @NamedQuery(name = "SiegeSocial.findByTelephone", query = "SELECT s FROM SiegeSocial s WHERE s.telephone = :telephone"),
    @NamedQuery(name = "SiegeSocial.findByAdresse", query = "SELECT s FROM SiegeSocial s WHERE s.adresse = :adresse")})
public class SiegeSocial implements Serializable {
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
    
    @Size(max = 2147483647)
    @Column(name = "code")
    private String code;
    
    @JoinColumn(name = "entreprise_id", referencedColumnName = "entreprise_id")
    @OneToOne
    @JsonBackReference
    private Entreprise entrepriseId;

    public SiegeSocial() {
    }

    public SiegeSocial(String filialeId) {
        this.filialeId = filialeId;
    }

    public String getFilialeId() {
        return filialeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if (!(object instanceof SiegeSocial)) {
            return false;
        }
        SiegeSocial other = (SiegeSocial) object;
        if ((this.filialeId == null && other.filialeId != null) || (this.filialeId != null && !this.filialeId.equals(other.filialeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.zero.risk.SiegeSocial[ filialeId=" + filialeId + " ]";
    }

    public Entreprise getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Entreprise entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

   
    
}
