package controller;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Movimento;

/**
 *
 * @author Kervini
 */
public class MovimentoJpaController implements Serializable {
    private EntityManagerFactory emf = null;
    
    public MovimentoJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void persist(Movimento m){
        EntityManager em = getEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }finally{
            em.close();
        }
    }
}
