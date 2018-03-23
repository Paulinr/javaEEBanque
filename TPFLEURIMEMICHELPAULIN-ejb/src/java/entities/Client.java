/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dmichel
 */
@Entity
@Table(name = "tbl_client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByClientId", query = "SELECT c FROM Client c WHERE c.id = :clientId"),
    @NamedQuery(name = "Client.findByNom", query = "SELECT c FROM Client c WHERE c.nom = :nom"),
    @NamedQuery(name = "Client.nbClients", query = "SELECT COUNT(c) FROM Client c"),
    @NamedQuery(name = "Client.findByPrenom", query = "SELECT c FROM Client c WHERE c.prenom = :prenom")})
      
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 15)
    @Column(name = "NIF",unique = true)
    private String nif;
    
    @Size(max = 30)
    @Column(name = "NOM")
    private String nom;
	
    @Size(max = 30)
    @Column(name = "PRENOM")
    private String prenom;
    
    @OneToMany(mappedBy = "client")
    private List<TransactionBancaire> transactions;

    public List<TransactionBancaire> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionBancaire> transactions) {
        this.transactions = transactions;
    }

    public Collection<CompteBancaire> getCompteBancaires() {
        return compteBancaires;
    }

    public void setCompteBancaires(Collection<CompteBancaire> compteBancaires) {
        this.compteBancaires = compteBancaires;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "client")
    private Collection<CompteBancaire> compteBancaires;
    
    @Embedded
    private Addresse addresse;
    
    @Size(max = 16)
    @Column(name = "TELEPHONE")
    private String phone;

    @Size(max = 40)
    @Column(name = "EMAIL",unique = true)
    private String email;

    public Client() {
        this.addresse = new Addresse();
        this.compteBancaires = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public Client(String nif, String nom, String prenom, Addresse addresse, String phone, String email) {
        this.nif = nif;
        this.nom = nom;
        this.prenom = prenom;
        this.addresse = addresse;
        this.phone = phone;
        this.email = email;
    }

    public Client(String nif, String nom, String prenom, String phone, String email) {
		this();
        this.nif = nif;
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.email = email;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Client[ id=" + id + " ]";
    }

    public Addresse getAddresse() {
        return addresse;
    }

    public void setAddresse(Addresse addresse) {
        this.addresse = addresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

}
