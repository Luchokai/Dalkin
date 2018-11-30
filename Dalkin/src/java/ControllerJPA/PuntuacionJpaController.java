/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Cliente;
import Model.LocalComida;
import Model.Puntuacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luisa
 */
public class PuntuacionJpaController implements Serializable {

    public PuntuacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Puntuacion puntuacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteId = puntuacion.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                puntuacion.setClienteId(clienteId);
            }
            LocalComida localComidaId = puntuacion.getLocalComidaId();
            if (localComidaId != null) {
                localComidaId = em.getReference(localComidaId.getClass(), localComidaId.getId());
                puntuacion.setLocalComidaId(localComidaId);
            }
            em.persist(puntuacion);
            if (clienteId != null) {
                clienteId.getPuntuacionCollection().add(puntuacion);
                clienteId = em.merge(clienteId);
            }
            if (localComidaId != null) {
                localComidaId.getPuntuacionCollection().add(puntuacion);
                localComidaId = em.merge(localComidaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Puntuacion puntuacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puntuacion persistentPuntuacion = em.find(Puntuacion.class, puntuacion.getId());
            Cliente clienteIdOld = persistentPuntuacion.getClienteId();
            Cliente clienteIdNew = puntuacion.getClienteId();
            LocalComida localComidaIdOld = persistentPuntuacion.getLocalComidaId();
            LocalComida localComidaIdNew = puntuacion.getLocalComidaId();
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                puntuacion.setClienteId(clienteIdNew);
            }
            if (localComidaIdNew != null) {
                localComidaIdNew = em.getReference(localComidaIdNew.getClass(), localComidaIdNew.getId());
                puntuacion.setLocalComidaId(localComidaIdNew);
            }
            puntuacion = em.merge(puntuacion);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getPuntuacionCollection().remove(puntuacion);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getPuntuacionCollection().add(puntuacion);
                clienteIdNew = em.merge(clienteIdNew);
            }
            if (localComidaIdOld != null && !localComidaIdOld.equals(localComidaIdNew)) {
                localComidaIdOld.getPuntuacionCollection().remove(puntuacion);
                localComidaIdOld = em.merge(localComidaIdOld);
            }
            if (localComidaIdNew != null && !localComidaIdNew.equals(localComidaIdOld)) {
                localComidaIdNew.getPuntuacionCollection().add(puntuacion);
                localComidaIdNew = em.merge(localComidaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = puntuacion.getId();
                if (findPuntuacion(id) == null) {
                    throw new NonexistentEntityException("The puntuacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puntuacion puntuacion;
            try {
                puntuacion = em.getReference(Puntuacion.class, id);
                puntuacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puntuacion with id " + id + " no longer exists.", enfe);
            }
            Cliente clienteId = puntuacion.getClienteId();
            if (clienteId != null) {
                clienteId.getPuntuacionCollection().remove(puntuacion);
                clienteId = em.merge(clienteId);
            }
            LocalComida localComidaId = puntuacion.getLocalComidaId();
            if (localComidaId != null) {
                localComidaId.getPuntuacionCollection().remove(puntuacion);
                localComidaId = em.merge(localComidaId);
            }
            em.remove(puntuacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Puntuacion> findPuntuacionEntities() {
        return findPuntuacionEntities(true, -1, -1);
    }

    public List<Puntuacion> findPuntuacionEntities(int maxResults, int firstResult) {
        return findPuntuacionEntities(false, maxResults, firstResult);
    }

    private List<Puntuacion> findPuntuacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Puntuacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Puntuacion findPuntuacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Puntuacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuntuacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Puntuacion> rt = cq.from(Puntuacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
