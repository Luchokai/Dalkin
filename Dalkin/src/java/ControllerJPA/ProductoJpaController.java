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
import Model.TipoComida;
import Model.TipoPreparacion;
import Model.LocalComida;
import Model.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luisa
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoComida tipoComidaId = producto.getTipoComidaId();
            if (tipoComidaId != null) {
                tipoComidaId = em.getReference(tipoComidaId.getClass(), tipoComidaId.getId());
                producto.setTipoComidaId(tipoComidaId);
            }
            TipoPreparacion tipoPreparacionId = producto.getTipoPreparacionId();
            if (tipoPreparacionId != null) {
                tipoPreparacionId = em.getReference(tipoPreparacionId.getClass(), tipoPreparacionId.getId());
                producto.setTipoPreparacionId(tipoPreparacionId);
            }
            LocalComida localId = producto.getLocalId();
            if (localId != null) {
                localId = em.getReference(localId.getClass(), localId.getId());
                producto.setLocalId(localId);
            }
            em.persist(producto);
            if (tipoComidaId != null) {
                tipoComidaId.getProductoCollection().add(producto);
                tipoComidaId = em.merge(tipoComidaId);
            }
            if (tipoPreparacionId != null) {
                tipoPreparacionId.getProductoCollection().add(producto);
                tipoPreparacionId = em.merge(tipoPreparacionId);
            }
            if (localId != null) {
                localId.getProductoCollection().add(producto);
                localId = em.merge(localId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            TipoComida tipoComidaIdOld = persistentProducto.getTipoComidaId();
            TipoComida tipoComidaIdNew = producto.getTipoComidaId();
            TipoPreparacion tipoPreparacionIdOld = persistentProducto.getTipoPreparacionId();
            TipoPreparacion tipoPreparacionIdNew = producto.getTipoPreparacionId();
            LocalComida localIdOld = persistentProducto.getLocalId();
            LocalComida localIdNew = producto.getLocalId();
            if (tipoComidaIdNew != null) {
                tipoComidaIdNew = em.getReference(tipoComidaIdNew.getClass(), tipoComidaIdNew.getId());
                producto.setTipoComidaId(tipoComidaIdNew);
            }
            if (tipoPreparacionIdNew != null) {
                tipoPreparacionIdNew = em.getReference(tipoPreparacionIdNew.getClass(), tipoPreparacionIdNew.getId());
                producto.setTipoPreparacionId(tipoPreparacionIdNew);
            }
            if (localIdNew != null) {
                localIdNew = em.getReference(localIdNew.getClass(), localIdNew.getId());
                producto.setLocalId(localIdNew);
            }
            producto = em.merge(producto);
            if (tipoComidaIdOld != null && !tipoComidaIdOld.equals(tipoComidaIdNew)) {
                tipoComidaIdOld.getProductoCollection().remove(producto);
                tipoComidaIdOld = em.merge(tipoComidaIdOld);
            }
            if (tipoComidaIdNew != null && !tipoComidaIdNew.equals(tipoComidaIdOld)) {
                tipoComidaIdNew.getProductoCollection().add(producto);
                tipoComidaIdNew = em.merge(tipoComidaIdNew);
            }
            if (tipoPreparacionIdOld != null && !tipoPreparacionIdOld.equals(tipoPreparacionIdNew)) {
                tipoPreparacionIdOld.getProductoCollection().remove(producto);
                tipoPreparacionIdOld = em.merge(tipoPreparacionIdOld);
            }
            if (tipoPreparacionIdNew != null && !tipoPreparacionIdNew.equals(tipoPreparacionIdOld)) {
                tipoPreparacionIdNew.getProductoCollection().add(producto);
                tipoPreparacionIdNew = em.merge(tipoPreparacionIdNew);
            }
            if (localIdOld != null && !localIdOld.equals(localIdNew)) {
                localIdOld.getProductoCollection().remove(producto);
                localIdOld = em.merge(localIdOld);
            }
            if (localIdNew != null && !localIdNew.equals(localIdOld)) {
                localIdNew.getProductoCollection().add(producto);
                localIdNew = em.merge(localIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            TipoComida tipoComidaId = producto.getTipoComidaId();
            if (tipoComidaId != null) {
                tipoComidaId.getProductoCollection().remove(producto);
                tipoComidaId = em.merge(tipoComidaId);
            }
            TipoPreparacion tipoPreparacionId = producto.getTipoPreparacionId();
            if (tipoPreparacionId != null) {
                tipoPreparacionId.getProductoCollection().remove(producto);
                tipoPreparacionId = em.merge(tipoPreparacionId);
            }
            LocalComida localId = producto.getLocalId();
            if (localId != null) {
                localId.getProductoCollection().remove(producto);
                localId = em.merge(localId);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
