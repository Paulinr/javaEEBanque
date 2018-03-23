/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Addresse;
import entities.Client;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dmichel
 */
@Stateless
@LocalBean
public class GestionnaireDeClient {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "TPFLEURIMEMICHELPAULIN-ejbPU")
    private EntityManager em;

    public void creerClient(Client client) {
        em.persist(client);
    }

    public List<Client> getAllClients() {
        Query query = em.createNamedQuery("Client.findAll");
        return query.getResultList();
    }
     public int getNbClients(){
        Query query = em.createNamedQuery("Client.nbClients");
        
        return ((Long) query.getSingleResult()).intValue();
    }
    
    public Client update(Client client) {
        return em.merge(client);
    }

    public void persit(Client client) {
        em.persist(client);
    }

    public Client getClient(Long idClient) {
        return em.find(Client.class, idClient);
    }
}
