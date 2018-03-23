/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author dmichel
 */
@Embeddable
public class Addresse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ville;
    private String rue;
    private int numero;

    public String getVille() {
        return ville;
    }

    public Addresse() {
    }

    public Addresse(String ville, String rue, int numero) {
        this.ville = ville;
        this.rue = rue;
        this.numero = numero;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
