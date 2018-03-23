/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dmichel
 */
@Entity
@Table(name = "tbl_comptebancaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompteBancaire.findAll", query = "SELECT c FROM CompteBancaire c"),
    @NamedQuery(name = "CompteBancaire.findByCompteId", query = "SELECT c FROM CompteBancaire c WHERE c.id = :compteBancaireId"),
     @NamedQuery(name = "CompteBancaire.findByCompteNoCpte", query = "SELECT c FROM CompteBancaire c WHERE c.numeroCompte =:noCompte"),
      @NamedQuery(name = "CompteBancaire.findByTypeCompte", query = "SELECT c FROM CompteBancaire c WHERE c.nom = :typeCompte"),
     @NamedQuery(name = "CompteBancaire.nbComptes", query = "SELECT COUNT(c) FROM CompteBancaire c"),
    @NamedQuery(name = "CompteBancaire.findCompteByClientId", query = "SELECT c FROM CompteBancaire c WHERE c.client.id = :clientId")
  })


public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Size(max = 16)
    @Column(unique = true,nullable = false)
    private String numeroCompte;
    private String nom;
    private float solde;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreated;

    public CompteBancaire() {
        transactions= new ArrayList<>();
    }

    public CompteBancaire(String numeroCompte,String nom, float solde, Client client, List<TransactionBancaire> transactions) {
        this.numeroCompte= numeroCompte;
        this.nom = nom;
        this.solde = solde;
        this.client = client;
        this.transactions = transactions;
    }

    public CompteBancaire(String numeroCompte, String nom, float solde, Client client) {
        this.numeroCompte = numeroCompte;
        this.nom = nom;
        this.solde = solde;
        this.client = client;
        this.dateCreated=new Date();
    }
    

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "comptebancaire")
    private List<TransactionBancaire> transactions;

    public CompteBancaire(String nom, float solde) {
        this();
        this.nom = nom;
        this.solde = solde;
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
        if (!(object instanceof CompteBancaire)) {
            return false;
        }
        CompteBancaire other = (CompteBancaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CompteBancaire[ id=" + id + " ]";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<TransactionBancaire> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionBancaire> transactions) {
        this.transactions = transactions;
    }

    public void deposer(float montant) {
        this.solde = this.solde + montant;
    }

    public float retirer(float montant) {
        if (montant <= solde) {
            solde -= montant;
            return solde;
        } else {
            return 0;
        }
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

}
