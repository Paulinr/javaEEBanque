/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.CompteBancaire;
import entities.TransactionBancaire;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dmichel
 */
@Stateless
@LocalBean
public class GestionnaireDeCompteBancaire {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "TPFLEURIMEMICHELPAULIN-ejbPU")
    private EntityManager em;

    public void creerCompte(CompteBancaire compteBancaire) {
        em.persist(compteBancaire);
    }

    public List<CompteBancaire> getAllComptes() {
        Query query = em.createNamedQuery("CompteBancaire.findAll");
        return query.getResultList();
    }
    
     public List<CompteBancaire> getLazyCptes(int start, int nbComptes){
        
        Query query =em.createNamedQuery("CompteBancaire.findAll");
        query.setFirstResult(start);
        query.setMaxResults(nbComptes);
        
        return query.getResultList();
    }
    
  public int getNbComptes(){
        Query query = em.createNamedQuery("CompteBancaire.nbComptes");
        
        return ((Long) query.getSingleResult()).intValue();
    }
    
    /**
     * Supprimer un compte bancaire
     * @param compte 
     */
    public void delete(CompteBancaire compte) {
        em.remove(em.merge(compte));
    }
    
    public void creerOperation(CompteBancaire compte, String descrption, float montant){
        TransactionBancaire tb = new TransactionBancaire(montant, descrption);
        compte.getTransactions().add(tb);
    }
    
    public CompteBancaire consulter(long id) {   
        return em.find(CompteBancaire.class, id);
    }

  
    public CompteBancaire crediterCompte(CompteBancaire compte, float montant){
        compte.deposer(montant);
        creerOperation(compte, "Dépot", montant);
        em.merge(compte);
        
        return update (compte);
    }
    
    public CompteBancaire debiterCompte(CompteBancaire compte, float montant){
        compte.retirer(montant);
        creerOperation(compte, "Retrait", montant);
        em.merge(compte);
        return update (compte);
    }
    
    
    public void transaction(int tb, CompteBancaire compte, float montant){
               
        if (tb == 0){
            compte = this.debiterCompte(compte, montant);
        }
        else 
            if (tb == 1) {
            compte= this.crediterCompte(compte, montant);      
            }
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération réussie !","La transaction a été effectuée");  
          
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 

    public CompteBancaire update(CompteBancaire comptebancaire) {
        return em.merge(comptebancaire);
    }

    public void persist(CompteBancaire compteBancaire) {
        em.persist(compteBancaire);
    }

    public CompteBancaire getCompteBancaire(Long idCompteBancaire) {
        return em.find(CompteBancaire.class, idCompteBancaire);
    }
    
     public void virement(Long idDebiteur,Long idCrediteur, float montant){
        
        CompteBancaire compteDebiteur = getCompteBancaire(idDebiteur);
        CompteBancaire compteCrediteur= getCompteBancaire(idCrediteur);
        
        compteDebiteur.retirer(montant);
        compteCrediteur.deposer(montant);
        
        creerOperation(compteDebiteur, "Virement effectué à "+compteCrediteur.getClient().getNom()+"-"+compteCrediteur.getClient().getPrenom(), montant);
        creerOperation(compteCrediteur, "Virement reçu de "+ compteDebiteur.getClient().getNom()+"-"+compteDebiteur.getClient().getPrenom(), montant);
        
        em.merge(compteDebiteur);
        em.merge(compteCrediteur);
    }
}
