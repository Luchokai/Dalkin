/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import Model.Comuna;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.LocalComida;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author luisa
 */
public class ComunaJpaController implements Serializable {

    public ComunaJpaController() {
    emf = Persistence.createEntityManagerFactory("DalkinPU");
}
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comuna comuna) {
        if (comuna.getLocalComidaCollection() == null) {
            comuna.setLocalComidaCollection(new ArrayList<LocalComida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<LocalComida> attachedLocalComidaCollection = new ArrayList<LocalComida>();
            for (LocalComida localComidaCollectionLocalComidaToAttach : comuna.getLocalComidaCollection()) {
                localComidaCollectionLocalComidaToAttach = em.getReference(localComidaCollectionLocalComidaToAttach.getClass(), localComidaCollectionLocalComidaToAttach.getId());
                attachedLocalComidaCollection.add(localComidaCollectionLocalComidaToAttach);
            }
            comuna.setLocalComidaCollection(attachedLocalComidaCollection);
            em.persist(comuna);
            for (LocalComida localComidaCollectionLocalComida : comuna.getLocalComidaCollection()) {
                Comuna oldComunaIdOfLocalComidaCollectionLocalComida = localComidaCollectionLocalComida.getComunaId();
                localComidaCollectionLocalComida.setComunaId(comuna);
                localComidaCollectionLocalComida = em.merge(localComidaCollectionLocalComida);
                if (oldComunaIdOfLocalComidaCollectionLocalComida != null) {
                    oldComunaIdOfLocalComidaCollectionLocalComida.getLocalComidaCollection().remove(localComidaCollectionLocalComida);
                    oldComunaIdOfLocalComidaCollectionLocalComida = em.merge(oldComunaIdOfLocalComidaCollectionLocalComida);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comuna comuna) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comuna persistentComuna = em.find(Comuna.class, comuna.getId());
            Collection<LocalComida> localComidaCollectionOld = persistentComuna.getLocalComidaCollection();
            Collection<LocalComida> localComidaCollectionNew = comuna.getLocalComidaCollection();
            Collection<LocalComida> attachedLocalComidaCollectionNew = new ArrayList<LocalComida>();
            for (LocalComida localComidaCollectionNewLocalComidaToAttach : localComidaCollectionNew) {
                localComidaCollectionNewLocalComidaToAttach = em.getReference(localComidaCollectionNewLocalComidaToAttach.getClass(), localComidaCollectionNewLocalComidaToAttach.getId());
                attachedLocalComidaCollectionNew.add(localComidaCollectionNewLocalComidaToAttach);
            }
            localComidaCollectionNew = attachedLocalComidaCollectionNew;
            comuna.setLocalComidaCollection(localComidaCollectionNew);
            comuna = em.merge(comuna);
            for (LocalComida localComidaCollectionOldLocalComida : localComidaCollectionOld) {
                if (!localComidaCollectionNew.contains(localComidaCollectionOldLocalComida)) {
                    localComidaCollectionOldLocalComida.setComunaId(null);
                    localComidaCollectionOldLocalComida = em.merge(localComidaCollectionOldLocalComida);
                }
            }
            for (LocalComida localComidaCollectionNewLocalComida : localComidaCollectionNew) {
                if (!localComidaCollectionOld.contains(localComidaCollectionNewLocalComida)) {
                    Comuna oldComunaIdOfLocalComidaCollectionNewLocalComida = localComidaCollectionNewLocalComida.getComunaId();
                    localComidaCollectionNewLocalComida.setComunaId(comuna);
                    localComidaCollectionNewLocalComida = em.merge(localComidaCollectionNewLocalComida);
                    if (oldComunaIdOfLocalComidaCollectionNewLocalComida != null && !oldComunaIdOfLocalComidaCollectionNewLocalComida.equals(comuna)) {
                        oldComunaIdOfLocalComidaCollectionNewLocalComida.getLocalComidaCollection().remove(localComidaCollectionNewLocalComida);
                        oldComunaIdOfLocalComidaCollectionNewLocalComida = em.merge(oldComunaIdOfLocalComidaCollectionNewLocalComida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comuna.getId();
                if (findComuna(id) == null) {
                    throw new NonexistentEntityException("The comuna with id " + id + " no longer exists.");
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
            Comuna comuna;
            try {
                comuna = em.getReference(Comuna.class, id);
                comuna.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comuna with id " + id + " no longer exists.", enfe);
            }
            Collection<LocalComida> localComidaCollection = comuna.getLocalComidaCollection();
            for (LocalComida localComidaCollectionLocalComida : localComidaCollection) {
                localComidaCollectionLocalComida.setComunaId(null);
                localComidaCollectionLocalComida = em.merge(localComidaCollectionLocalComida);
            }
            em.remove(comuna);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comuna> findComunaEntities() {
        return findComunaEntities(true, -1, -1);
    }

    public List<Comuna> findComunaEntities(int maxResults, int firstResult) {
        return findComunaEntities(false, maxResults, firstResult);
    }

    private List<Comuna> findComunaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comuna.class));
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

    public Comuna findComuna(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comuna.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comuna> rt = cq.from(Comuna.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
