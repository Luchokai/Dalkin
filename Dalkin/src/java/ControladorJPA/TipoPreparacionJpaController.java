/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorJPA;

import ControladorJPA.exceptions.NonexistentEntityException;
import ControladorJPA.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Producto;
import Modelo.TipoPreparacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author luisa
 */
public class TipoPreparacionJpaController implements Serializable {

    public TipoPreparacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPreparacion tipoPreparacion) throws RollbackFailureException, Exception {
        if (tipoPreparacion.getProductoCollection() == null) {
            tipoPreparacion.setProductoCollection(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : tipoPreparacion.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getId());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            tipoPreparacion.setProductoCollection(attachedProductoCollection);
            em.persist(tipoPreparacion);
            for (Producto productoCollectionProducto : tipoPreparacion.getProductoCollection()) {
                TipoPreparacion oldTipoPreparacionIdOfProductoCollectionProducto = productoCollectionProducto.getTipoPreparacionId();
                productoCollectionProducto.setTipoPreparacionId(tipoPreparacion);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldTipoPreparacionIdOfProductoCollectionProducto != null) {
                    oldTipoPreparacionIdOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldTipoPreparacionIdOfProductoCollectionProducto = em.merge(oldTipoPreparacionIdOfProductoCollectionProducto);
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

    public void edit(TipoPreparacion tipoPreparacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoPreparacion persistentTipoPreparacion = em.find(TipoPreparacion.class, tipoPreparacion.getId());
            Collection<Producto> productoCollectionOld = persistentTipoPreparacion.getProductoCollection();
            Collection<Producto> productoCollectionNew = tipoPreparacion.getProductoCollection();
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getId());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            tipoPreparacion.setProductoCollection(productoCollectionNew);
            tipoPreparacion = em.merge(tipoPreparacion);
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    productoCollectionOldProducto.setTipoPreparacionId(null);
                    productoCollectionOldProducto = em.merge(productoCollectionOldProducto);
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    TipoPreparacion oldTipoPreparacionIdOfProductoCollectionNewProducto = productoCollectionNewProducto.getTipoPreparacionId();
                    productoCollectionNewProducto.setTipoPreparacionId(tipoPreparacion);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldTipoPreparacionIdOfProductoCollectionNewProducto != null && !oldTipoPreparacionIdOfProductoCollectionNewProducto.equals(tipoPreparacion)) {
                        oldTipoPreparacionIdOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldTipoPreparacionIdOfProductoCollectionNewProducto = em.merge(oldTipoPreparacionIdOfProductoCollectionNewProducto);
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
                Integer id = tipoPreparacion.getId();
                if (findTipoPreparacion(id) == null) {
                    throw new NonexistentEntityException("The tipoPreparacion with id " + id + " no longer exists.");
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
            TipoPreparacion tipoPreparacion;
            try {
                tipoPreparacion = em.getReference(TipoPreparacion.class, id);
                tipoPreparacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPreparacion with id " + id + " no longer exists.", enfe);
            }
            Collection<Producto> productoCollection = tipoPreparacion.getProductoCollection();
            for (Producto productoCollectionProducto : productoCollection) {
                productoCollectionProducto.setTipoPreparacionId(null);
                productoCollectionProducto = em.merge(productoCollectionProducto);
            }
            em.remove(tipoPreparacion);
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

    public List<TipoPreparacion> findTipoPreparacionEntities() {
        return findTipoPreparacionEntities(true, -1, -1);
    }

    public List<TipoPreparacion> findTipoPreparacionEntities(int maxResults, int firstResult) {
        return findTipoPreparacionEntities(false, maxResults, firstResult);
    }

    private List<TipoPreparacion> findTipoPreparacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPreparacion.class));
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

    public TipoPreparacion findTipoPreparacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPreparacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPreparacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPreparacion> rt = cq.from(TipoPreparacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
