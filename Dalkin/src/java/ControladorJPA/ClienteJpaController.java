/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorJPA;

import ControladorJPA.exceptions.NonexistentEntityException;
import ControladorJPA.exceptions.RollbackFailureException;
import Modelo.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Puntuacion;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.LocalComida;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author luisa
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws RollbackFailureException, Exception {
        if (cliente.getPuntuacionCollection() == null) {
            cliente.setPuntuacionCollection(new ArrayList<Puntuacion>());
        }
        if (cliente.getLocalComidaCollection() == null) {
            cliente.setLocalComidaCollection(new ArrayList<LocalComida>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Puntuacion> attachedPuntuacionCollection = new ArrayList<Puntuacion>();
            for (Puntuacion puntuacionCollectionPuntuacionToAttach : cliente.getPuntuacionCollection()) {
                puntuacionCollectionPuntuacionToAttach = em.getReference(puntuacionCollectionPuntuacionToAttach.getClass(), puntuacionCollectionPuntuacionToAttach.getId());
                attachedPuntuacionCollection.add(puntuacionCollectionPuntuacionToAttach);
            }
            cliente.setPuntuacionCollection(attachedPuntuacionCollection);
            Collection<LocalComida> attachedLocalComidaCollection = new ArrayList<LocalComida>();
            for (LocalComida localComidaCollectionLocalComidaToAttach : cliente.getLocalComidaCollection()) {
                localComidaCollectionLocalComidaToAttach = em.getReference(localComidaCollectionLocalComidaToAttach.getClass(), localComidaCollectionLocalComidaToAttach.getId());
                attachedLocalComidaCollection.add(localComidaCollectionLocalComidaToAttach);
            }
            cliente.setLocalComidaCollection(attachedLocalComidaCollection);
            em.persist(cliente);
            for (Puntuacion puntuacionCollectionPuntuacion : cliente.getPuntuacionCollection()) {
                Cliente oldClienteIdOfPuntuacionCollectionPuntuacion = puntuacionCollectionPuntuacion.getClienteId();
                puntuacionCollectionPuntuacion.setClienteId(cliente);
                puntuacionCollectionPuntuacion = em.merge(puntuacionCollectionPuntuacion);
                if (oldClienteIdOfPuntuacionCollectionPuntuacion != null) {
                    oldClienteIdOfPuntuacionCollectionPuntuacion.getPuntuacionCollection().remove(puntuacionCollectionPuntuacion);
                    oldClienteIdOfPuntuacionCollectionPuntuacion = em.merge(oldClienteIdOfPuntuacionCollectionPuntuacion);
                }
            }
            for (LocalComida localComidaCollectionLocalComida : cliente.getLocalComidaCollection()) {
                Cliente oldClienteIdOfLocalComidaCollectionLocalComida = localComidaCollectionLocalComida.getClienteId();
                localComidaCollectionLocalComida.setClienteId(cliente);
                localComidaCollectionLocalComida = em.merge(localComidaCollectionLocalComida);
                if (oldClienteIdOfLocalComidaCollectionLocalComida != null) {
                    oldClienteIdOfLocalComidaCollectionLocalComida.getLocalComidaCollection().remove(localComidaCollectionLocalComida);
                    oldClienteIdOfLocalComidaCollectionLocalComida = em.merge(oldClienteIdOfLocalComidaCollectionLocalComida);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Collection<Puntuacion> puntuacionCollectionOld = persistentCliente.getPuntuacionCollection();
            Collection<Puntuacion> puntuacionCollectionNew = cliente.getPuntuacionCollection();
            Collection<LocalComida> localComidaCollectionOld = persistentCliente.getLocalComidaCollection();
            Collection<LocalComida> localComidaCollectionNew = cliente.getLocalComidaCollection();
            Collection<Puntuacion> attachedPuntuacionCollectionNew = new ArrayList<Puntuacion>();
            for (Puntuacion puntuacionCollectionNewPuntuacionToAttach : puntuacionCollectionNew) {
                puntuacionCollectionNewPuntuacionToAttach = em.getReference(puntuacionCollectionNewPuntuacionToAttach.getClass(), puntuacionCollectionNewPuntuacionToAttach.getId());
                attachedPuntuacionCollectionNew.add(puntuacionCollectionNewPuntuacionToAttach);
            }
            puntuacionCollectionNew = attachedPuntuacionCollectionNew;
            cliente.setPuntuacionCollection(puntuacionCollectionNew);
            Collection<LocalComida> attachedLocalComidaCollectionNew = new ArrayList<LocalComida>();
            for (LocalComida localComidaCollectionNewLocalComidaToAttach : localComidaCollectionNew) {
                localComidaCollectionNewLocalComidaToAttach = em.getReference(localComidaCollectionNewLocalComidaToAttach.getClass(), localComidaCollectionNewLocalComidaToAttach.getId());
                attachedLocalComidaCollectionNew.add(localComidaCollectionNewLocalComidaToAttach);
            }
            localComidaCollectionNew = attachedLocalComidaCollectionNew;
            cliente.setLocalComidaCollection(localComidaCollectionNew);
            cliente = em.merge(cliente);
            for (Puntuacion puntuacionCollectionOldPuntuacion : puntuacionCollectionOld) {
                if (!puntuacionCollectionNew.contains(puntuacionCollectionOldPuntuacion)) {
                    puntuacionCollectionOldPuntuacion.setClienteId(null);
                    puntuacionCollectionOldPuntuacion = em.merge(puntuacionCollectionOldPuntuacion);
                }
            }
            for (Puntuacion puntuacionCollectionNewPuntuacion : puntuacionCollectionNew) {
                if (!puntuacionCollectionOld.contains(puntuacionCollectionNewPuntuacion)) {
                    Cliente oldClienteIdOfPuntuacionCollectionNewPuntuacion = puntuacionCollectionNewPuntuacion.getClienteId();
                    puntuacionCollectionNewPuntuacion.setClienteId(cliente);
                    puntuacionCollectionNewPuntuacion = em.merge(puntuacionCollectionNewPuntuacion);
                    if (oldClienteIdOfPuntuacionCollectionNewPuntuacion != null && !oldClienteIdOfPuntuacionCollectionNewPuntuacion.equals(cliente)) {
                        oldClienteIdOfPuntuacionCollectionNewPuntuacion.getPuntuacionCollection().remove(puntuacionCollectionNewPuntuacion);
                        oldClienteIdOfPuntuacionCollectionNewPuntuacion = em.merge(oldClienteIdOfPuntuacionCollectionNewPuntuacion);
                    }
                }
            }
            for (LocalComida localComidaCollectionOldLocalComida : localComidaCollectionOld) {
                if (!localComidaCollectionNew.contains(localComidaCollectionOldLocalComida)) {
                    localComidaCollectionOldLocalComida.setClienteId(null);
                    localComidaCollectionOldLocalComida = em.merge(localComidaCollectionOldLocalComida);
                }
            }
            for (LocalComida localComidaCollectionNewLocalComida : localComidaCollectionNew) {
                if (!localComidaCollectionOld.contains(localComidaCollectionNewLocalComida)) {
                    Cliente oldClienteIdOfLocalComidaCollectionNewLocalComida = localComidaCollectionNewLocalComida.getClienteId();
                    localComidaCollectionNewLocalComida.setClienteId(cliente);
                    localComidaCollectionNewLocalComida = em.merge(localComidaCollectionNewLocalComida);
                    if (oldClienteIdOfLocalComidaCollectionNewLocalComida != null && !oldClienteIdOfLocalComidaCollectionNewLocalComida.equals(cliente)) {
                        oldClienteIdOfLocalComidaCollectionNewLocalComida.getLocalComidaCollection().remove(localComidaCollectionNewLocalComida);
                        oldClienteIdOfLocalComidaCollectionNewLocalComida = em.merge(oldClienteIdOfLocalComidaCollectionNewLocalComida);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Collection<Puntuacion> puntuacionCollection = cliente.getPuntuacionCollection();
            for (Puntuacion puntuacionCollectionPuntuacion : puntuacionCollection) {
                puntuacionCollectionPuntuacion.setClienteId(null);
                puntuacionCollectionPuntuacion = em.merge(puntuacionCollectionPuntuacion);
            }
            Collection<LocalComida> localComidaCollection = cliente.getLocalComidaCollection();
            for (LocalComida localComidaCollectionLocalComida : localComidaCollection) {
                localComidaCollectionLocalComida.setClienteId(null);
                localComidaCollectionLocalComida = em.merge(localComidaCollectionLocalComida);
            }
            em.remove(cliente);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
