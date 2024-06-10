/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.Pessoa;

/**
 *
 * @author Kervini
 */
public class PessoaJpaController {
    private EntityManagerFactory emf = null;
    
    public PessoaJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public Pessoa findById(int id){
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Pessoa.findById");
        q.setParameter("id", id);
        
        return (Pessoa) q.getSingleResult();
    }
}
