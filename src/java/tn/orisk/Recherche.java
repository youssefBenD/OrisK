/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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

/**
 *
 * @author user
 */
@Entity
@Table(name = "recherche")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recherche.findAll", query = "SELECT r FROM Recherche r"),
    @NamedQuery(name = "Recherche.findByRechercheId", query = "SELECT r FROM Recherche r WHERE r.rechercheId = :rechercheId"),
    @NamedQuery(name = "Recherche.findByClientId", query = "SELECT r FROM Recherche r WHERE r.clientId = :clientId"),
    @NamedQuery(name = "Recherche.findByDate", query = "SELECT r FROM Recherche r WHERE r.date = :date"),
    @NamedQuery(name = "Recherche.findByCleRecherche", query = "SELECT r FROM Recherche r WHERE r.cleRecherche = :cleRecherche"),
    @NamedQuery(name = "Recherche.findBySerial", query = "SELECT r FROM Recherche r WHERE r.serial = :serial")})
public class Recherche implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "recherche_id")
    private String rechercheId;
    
    @Size(max = 2147483647)
    @Column(name = "client_id")
    private String clientId;
    
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Size(max = 2147483647)
    @Column(name = "cle_recherche")
    private String cleRecherche;
    
    @Basic(optional = false)
    @Column(name = "serial")
    private int serial;
    
    @JoinColumn(name = "filiale_id", referencedColumnName = "filiale_id")
    @ManyToOne
    @JsonBackReference
    private Filiale filialeId;

    public Recherche() {
    }

    public Recherche(String rechercheId) {
        this.rechercheId = rechercheId;
    }

    public Recherche(String rechercheId, int serial) {
        this.rechercheId = rechercheId;
        this.serial = serial;
    }

    public String getRechercheId() {
        return rechercheId;
    }

    public void setRechercheId(String rechercheId) {
        this.rechercheId = rechercheId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public Filiale getFilialeId() {
        return filialeId;
    }

    public void setFilialeId(Filiale filialeId) {
        this.filialeId = filialeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rechercheId != null ? rechercheId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recherche)) {
            return false;
        }
        Recherche other = (Recherche) object;
        if ((this.rechercheId == null && other.rechercheId != null) || (this.rechercheId != null && !this.rechercheId.equals(other.rechercheId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.orisk.Recherche[ rechercheId=" + rechercheId + " ]";
    }
    
}
