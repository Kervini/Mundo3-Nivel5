package controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.Produto;

public class ProdutoJpaController {
    private EntityManagerFactory emf = null;
    
    public ProdutoJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public List<Produto> findAll(){
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Produto.findAll");
        
        return q.getResultList();
    }
    
    public Produto findById(int id){
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Produto.findById");
        q.setParameter("id", id);
        
        return (Produto) q.getSingleResult();
    }
    
    public void merge(Produto p){
        EntityManager em = getEntityManager();        
        
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }finally{
            em.close();
        }
    }
}
