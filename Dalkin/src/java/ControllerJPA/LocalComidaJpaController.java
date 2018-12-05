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
import Model.Comuna;
import Model.LocalComida;
import Model.Puntuacion;
import java.util.ArrayList;
import java.util.Collection;
import Model.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author luisa
 */
public class LocalComidaJpaController implements Serializable {

    public LocalComidaJpaController() {
    emf = Persistence.createEntityManagerFactory("DalkinPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LocalComida localComida) {
        if (localComida.getPuntuacionCollection() == null) {
            localComida.setPuntuacionCollection(new ArrayList<Puntuacion>());
        }
        if (localComida.getProductoCollection() == null) {
            localComida.setProductoCollection(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteId = localComida.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                localComida.setClienteId(clienteId);
            }
            Comuna comunaId = localComida.getComunaId();
            if (comunaId != null) {
                comunaId = em.getReference(comunaId.getClass(), comunaId.getId());
                localComida.setComunaId(comunaId);
            }
            Collection<Puntuacion> attachedPuntuacionCollection = new ArrayList<Puntuacion>();
            for (Puntuacion puntuacionCollectionPuntuacionToAttach : localComida.getPuntuacionCollection()) {
                puntuacionCollectionPuntuacionToAttach = em.getReference(puntuacionCollectionPuntuacionToAttach.getClass(), puntuacionCollectionPuntuacionToAttach.getId());
                attachedPuntuacionCollection.add(puntuacionCollectionPuntuacionToAttach);
            }
            localComida.setPuntuacionCollection(attachedPuntuacionCollection);
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : localComida.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getId());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            localComida.setProductoCollection(attachedProductoCollection);
            em.persist(localComida);
            if (clienteId != null) {
                clienteId.getLocalComidaCollection().add(localComida);
                clienteId = em.merge(clienteId);
            }
            if (comunaId != null) {
                comunaId.getLocalComidaCollection().add(localComida);
                comunaId = em.merge(comunaId);
            }
            for (Puntuacion puntuacionCollectionPuntuacion : localComida.getPuntuacionCollection()) {
                LocalComida oldLocalComidaIdOfPuntuacionCollectionPuntuacion = puntuacionCollectionPuntuacion.getLocalComidaId();
                puntuacionCollectionPuntuacion.setLocalComidaId(localComida);
                puntuacionCollectionPuntuacion = em.merge(puntuacionCollectionPuntuacion);
                if (oldLocalComidaIdOfPuntuacionCollectionPuntuacion != null) {
                    oldLocalComidaIdOfPuntuacionCollectionPuntuacion.getPuntuacionCollection().remove(puntuacionCollectionPuntuacion);
                    oldLocalComidaIdOfPuntuacionCollectionPuntuacion = em.merge(oldLocalComidaIdOfPuntuacionCollectionPuntuacion);
                }
            }
            for (Producto productoCollectionProducto : localComida.getProductoCollection()) {
                LocalComida oldLocalIdOfProductoCollectionProducto = productoCollectionProducto.getLocalId();
                productoCollectionProducto.setLocalId(localComida);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldLocalIdOfProductoCollectionProducto != null) {
                    oldLocalIdOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldLocalIdOfProductoCollectionProducto = em.merge(oldLocalIdOfProductoCollectionProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LocalComida localComida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LocalComida persistentLocalComida = em.find(LocalComida.class, localComida.getId());
            Cliente clienteIdOld = persistentLocalComida.getClienteId();
            Cliente clienteIdNew = localComida.getClienteId();
            Comuna comunaIdOld = persistentLocalComida.getComunaId();
            Comuna comunaIdNew = localComida.getComunaId();
            Collection<Puntuacion> puntuacionCollectionOld = persistentLocalComida.getPuntuacionCollection();
            Collection<Puntuacion> puntuacionCollectionNew = localComida.getPuntuacionCollection();
            Collection<Producto> productoCollectionOld = persistentLocalComida.getProductoCollection();
            Collection<Producto> productoCollectionNew = localComida.getProductoCollection();
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                localComida.setClienteId(clienteIdNew);
            }
            if (comunaIdNew != null) {
                comunaIdNew = em.getReference(comunaIdNew.getClass(), comunaIdNew.getId());
                localComida.setComunaId(comunaIdNew);
            }
            Collection<Puntuacion> attachedPuntuacionCollectionNew = new ArrayList<Puntuacion>();
            for (Puntuacion puntuacionCollectionNewPuntuacionToAttach : puntuacionCollectionNew) {
                puntuacionCollectionNewPuntuacionToAttach = em.getReference(puntuacionCollectionNewPuntuacionToAttach.getClass(), puntuacionCollectionNewPuntuacionToAttach.getId());
                attachedPuntuacionCollectionNew.add(puntuacionCollectionNewPuntuacionToAttach);
            }
            puntuacionCollectionNew = attachedPuntuacionCollectionNew;
            localComida.setPuntuacionCollection(puntuacionCollectionNew);
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getId());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            localComida.setProductoCollection(productoCollectionNew);
            localComida = em.merge(localComida);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getLocalComidaCollection().remove(localComida);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getLocalComidaCollection().add(localComida);
                clienteIdNew = em.merge(clienteIdNew);
            }
            if (comunaIdOld != null && !comunaIdOld.equals(comunaIdNew)) {
                comunaIdOld.getLocalComidaCollection().remove(localComida);
                comunaIdOld = em.merge(comunaIdOld);
            }
            if (comunaIdNew != null && !comunaIdNew.equals(comunaIdOld)) {
                comunaIdNew.getLocalComidaCollection().add(localComida);
                comunaIdNew = em.merge(comunaIdNew);
            }
            for (Puntuacion puntuacionCollectionOldPuntuacion : puntuacionCollectionOld) {
                if (!puntuacionCollectionNew.contains(puntuacionCollectionOldPuntuacion)) {
                    puntuacionCollectionOldPuntuacion.setLocalComidaId(null);
                    puntuacionCollectionOldPuntuacion = em.merge(puntuacionCollectionOldPuntuacion);
                }
            }
            for (Puntuacion puntuacionCollectionNewPuntuacion : puntuacionCollectionNew) {
                if (!puntuacionCollectionOld.contains(puntuacionCollectionNewPuntuacion)) {
                    LocalComida oldLocalComidaIdOfPuntuacionCollectionNewPuntuacion = puntuacionCollectionNewPuntuacion.getLocalComidaId();
                    puntuacionCollectionNewPuntuacion.setLocalComidaId(localComida);
                    puntuacionCollectionNewPuntuacion = em.merge(puntuacionCollectionNewPuntuacion);
                    if (oldLocalComidaIdOfPuntuacionCollectionNewPuntuacion != null && !oldLocalComidaIdOfPuntuacionCollectionNewPuntuacion.equals(localComida)) {
                        oldLocalComidaIdOfPuntuacionCollectionNewPuntuacion.getPuntuacionCollection().remove(puntuacionCollectionNewPuntuacion);
                        oldLocalComidaIdOfPuntuacionCollectionNewPuntuacion = em.merge(oldLocalComidaIdOfPuntuacionCollectionNewPuntuacion);
                    }
                }
            }
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    productoCollectionOldProducto.setLocalId(null);
                    productoCollectionOldProducto = em.merge(productoCollectionOldProducto);
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    LocalComida oldLocalIdOfProductoCollectionNewProducto = productoCollectionNewProducto.getLocalId();
                    productoCollectionNewProducto.setLocalId(localComida);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldLocalIdOfProductoCollectionNewProducto != null && !oldLocalIdOfProductoCollectionNewProducto.equals(localComida)) {
                        oldLocalIdOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldLocalIdOfProductoCollectionNewProducto = em.merge(oldLocalIdOfProductoCollectionNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = localComida.getId();
                if (findLocalComida(id) == null) {
                    throw new NonexistentEntityException("The localComida with id " + id + " no longer exists.");
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
            LocalComida localComida;
            try {
                localComida = em.getReference(LocalComida.class, id);
                localComida.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The localComida with id " + id + " no longer exists.", enfe);
            }
            Cliente clienteId = localComida.getClienteId();
            if (clienteId != null) {
                clienteId.getLocalComidaCollection().remove(localComida);
                clienteId = em.merge(clienteId);
            }
            Comuna comunaId = localComida.getComunaId();
            if (comunaId != null) {
                comunaId.getLocalComidaCollection().remove(localComida);
                comunaId = em.merge(comunaId);
            }
            Collection<Puntuacion> puntuacionCollection = localComida.getPuntuacionCollection();
            for (Puntuacion puntuacionCollectionPuntuacion : puntuacionCollection) {
                puntuacionCollectionPuntuacion.setLocalComidaId(null);
                puntuacionCollectionPuntuacion = em.merge(puntuacionCollectionPuntuacion);
            }
            Collection<Producto> productoCollection = localComida.getProductoCollection();
            for (Producto productoCollectionProducto : productoCollection) {
                productoCollectionProducto.setLocalId(null);
                productoCollectionProducto = em.merge(productoCollectionProducto);
            }
            em.remove(localComida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LocalComida> findLocalComidaEntities() {
        return findLocalComidaEntities(true, -1, -1);
    }

    public List<LocalComida> findLocalComidaEntities(int maxResults, int firstResult) {
        return findLocalComidaEntities(false, maxResults, firstResult);
    }

    private List<LocalComida> findLocalComidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LocalComida.class));
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

    public LocalComida findLocalComida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LocalComida.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocalComidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LocalComida> rt = cq.from(LocalComida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
