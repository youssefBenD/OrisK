/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "authentification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authentification.findAll", query = "SELECT a FROM Authentification a"),
    @NamedQuery(name = "Authentification.findByUtilisateurId", query = "SELECT a FROM Authentification a WHERE a.utilisateurId = :utilisateurId"),
    @NamedQuery(name = "Authentification.findByRole", query = "SELECT a FROM Authentification a WHERE a.role = :role"),
    @NamedQuery(name = "Authentification.findByAuthToken", query = "SELECT a FROM Authentification a WHERE a.authToken = :authToken"),
    @NamedQuery(name = "Authentification.findByAuthentificationId", query = "SELECT a FROM Authentification a WHERE a.authentificationId = :authentificationId")})
public class Authentification implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence_authentification",
            sequenceName = "sequence_authentification",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence_authentification")
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "authentification_id")
    private String authentificationId;
    
    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "utilisateur_id")
    private String utilisateurId;
    
    @Size(max = 2147483647)
    @Column(name = "role")
    private String role;
    
    @Size(max = 2147483647)
    @Column(name = "auth_token")
    private String authToken;
    

    public Authentification() {
    }

    public Authentification(String utilisateurId, String role, String authToken) {
        this.utilisateurId = utilisateurId;
        this.role = role;
        this.authToken = authToken;
    }
    
    

    public Authentification(String authentificationId) {
        this.authentificationId = authentificationId;
    }

    public String getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(String utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthentificationId() {
        return authentificationId;
    }

    public void setAuthentificationId(String authentificationId) {
        this.authentificationId = authentificationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authentificationId != null ? authentificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authentification)) {
            return false;
        }
        Authentification other = (Authentification) object;
        if ((this.authentificationId == null && other.authentificationId != null) || (this.authentificationId != null && !this.authentificationId.equals(other.authentificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.orisk.Authentification[ authentificationId=" + authentificationId + " ]";
    }
    
}
