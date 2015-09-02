/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author user
 */
@Embeddable
public class RecherchePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "client_id")
    private String clientId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "filiale_id")
    private String filialeId;

    public RecherchePK() {
    }

    public RecherchePK(String clientId, String filialeId) {
        this.clientId = clientId;
        this.filialeId = filialeId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFilialeId() {
        return filialeId;
    }

    public void setFilialeId(String filialeId) {
        this.filialeId = filialeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientId != null ? clientId.hashCode() : 0);
        hash += (filialeId != null ? filialeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecherchePK)) {
            return false;
        }
        RecherchePK other = (RecherchePK) object;
        if ((this.clientId == null && other.clientId != null) || (this.clientId != null && !this.clientId.equals(other.clientId))) {
            return false;
        }
        if ((this.filialeId == null && other.filialeId != null) || (this.filialeId != null && !this.filialeId.equals(other.filialeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.zero.risk.RecherchePK[ clientId=" + clientId + ", filialeId=" + filialeId + " ]";
    }
    
}
