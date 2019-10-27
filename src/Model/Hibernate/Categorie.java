package Model.Hibernate;
// Generated Oct 26, 2019, 5:40:20 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Categorie generated by hbm2java
 */
public class Categorie  implements java.io.Serializable {


     private String categorie;
     private Set<Materiels> materielses = new HashSet<Materiels>(0);

    public Categorie() {
    }

	
    public Categorie(String categorie) {
        this.categorie = categorie;
    }
    public Categorie(String categorie, Set<Materiels> materielses) {
       this.categorie = categorie;
       this.materielses = materielses;
    }
   
    public String getCategorie() {
        return this.categorie;
    }
    
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public Set<Materiels> getMaterielses() {
        return this.materielses;
    }
    
    public void setMaterielses(Set<Materiels> materielses) {
        this.materielses = materielses;
    }




}


