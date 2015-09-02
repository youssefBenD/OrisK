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
public class AbonnementPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "pack_id")
    private String packId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "entreprise_id")
    private String entrepriseId;

    public AbonnementPK() {
    }

    public AbonnementPK(String packId, String entrepriseId) {
        this.packId = packId;
        this.entrepriseId = entrepriseId;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(String entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (packId != null ? packId.hashCode() : 0);
        hash += (entrepriseId != null ? entrepriseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbonnementPK)) {
            return false;
        }
        AbonnementPK other = (AbonnementPK) object;
        if ((this.packId == null && other.packId != null) || (this.packId != null && !this.packId.equals(other.packId))) {
            return false;
        }
        if ((this.entrepriseId == null && other.entrepriseId != null) || (this.entrepriseId != null && !this.entrepriseId.equals(other.entrepriseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.orisk.AbonnementPK[ packId=" + packId + ", entrepriseId=" + entrepriseId + " ]";
    }
    
}
