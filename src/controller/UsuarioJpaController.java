package controller;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.Usuario;

public class UsuarioJpaController implements Serializable {
    private EntityManagerFactory emf = null;
    
    public UsuarioJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public Usuario getUsuario(String login, String senha){
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Usuario.findUsuario");
        q.setParameter("login", login);
        q.setParameter("senha", senha);
        
        return (Usuario) q.getSingleResult();
    }
}
