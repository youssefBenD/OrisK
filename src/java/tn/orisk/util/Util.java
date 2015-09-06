/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.orisk.util;

/**
 *
 * @author user
 */
public class Util {
    private String relname;
    private String filiale_id;

    public Util() {
    }

    @Override
    public String toString() {
        return "Util{" + "relname=" + relname + ", filiale_id=" + filiale_id + '}';
    }

    public String getRelname() {
        return relname;
    }

    public void setRelname(String relname) {
        this.relname = relname;
    }

    public String getFiliale_id() {
        return filiale_id;
    }

    public void setFiliale_id(String filiale_id) {
        this.filiale_id = filiale_id;
    }
    
    
    
}
