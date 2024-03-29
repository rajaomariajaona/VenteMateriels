package Model.Hibernate;
// Generated Oct 26, 2019, 5:40:20 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Province generated by hbm2java
 */
public class Province  implements java.io.Serializable {


     private String province;
     private Set<Client> clients = new HashSet<Client>(0);

    public Province() {
    }

	
    public Province(String province) {
        this.province = province;
    }
    public Province(String province, Set<Client> clients) {
       this.province = province;
       this.clients = clients;
    }
   
    public String getProvince() {
        return this.province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    public Set<Client> getClients() {
        return this.clients;
    }
    
    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }




}


