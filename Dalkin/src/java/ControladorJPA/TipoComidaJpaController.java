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
import Modelo.TipoComida;
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
public class TipoComidaJpaController implements Serializable {

    public TipoComidaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoComida tipoComida) throws RollbackFailureException, Exception {
        if (tipoComida.getProductoCollection() == null) {
            tipoComida.setProductoCollection(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : tipoComida.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getId());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            tipoComida.setProductoCollection(attachedProductoCollection);
            em.persist(tipoComida);
            for (Producto productoCollectionProducto : tipoComida.getProductoCollection()) {
                TipoComida oldTipoComidaIdOfProductoCollectionProducto = productoCollectionProducto.getTipoComidaId();
                productoCollectionProducto.setTipoComidaId(tipoComida);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldTipoComidaIdOfProductoCollectionProducto != null) {
                    oldTipoComidaIdOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldTipoComidaIdOfProductoCollectionProducto = em.merge(oldTipoComidaIdOfProductoCollectionProducto);
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

    public void edit(TipoComida tipoComida) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoComida persistentTipoComida = em.find(TipoComida.class, tipoComida.getId());
            Collection<Producto> productoCollectionOld = persistentTipoComida.getProductoCollection();
            Collection<Producto> productoCollectionNew = tipoComida.getProductoCollection();
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getId());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            tipoComida.setProductoCollection(productoCollectionNew);
            tipoComida = em.merge(tipoComida);
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    productoCollectionOldProducto.setTipoComidaId(null);
                    productoCollectionOldProducto = em.merge(productoCollectionOldProducto);
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    TipoComida oldTipoComidaIdOfProductoCollectionNewProducto = productoCollectionNewProducto.getTipoComidaId();
                    productoCollectionNewProducto.setTipoComidaId(tipoComida);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldTipoComidaIdOfProductoCollectionNewProducto != null && !oldTipoComidaIdOfProductoCollectionNewProducto.equals(tipoComida)) {
                        oldTipoComidaIdOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldTipoComidaIdOfProductoCollectionNewProducto = em.merge(oldTipoComidaIdOfProductoCollectionNewProducto);
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
                Integer id = tipoComida.getId();
                if (findTipoComida(id) == null) {
                    throw new NonexistentEntityException("The tipoComida with id " + id + " no longer exists.");
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
            TipoComida tipoComida;
            try {
                tipoComida = em.getReference(TipoComida.class, id);
                tipoComida.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoComida with id " + id + " no longer exists.", enfe);
            }
            Collection<Producto> productoCollection = tipoComida.getProductoCollection();
            for (Producto productoCollectionProducto : productoCollection) {
                productoCollectionProducto.setTipoComidaId(null);
                productoCollectionProducto = em.merge(productoCollectionProducto);
            }
            em.remove(tipoComida);
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

    public List<TipoComida> findTipoComidaEntities() {
        return findTipoComidaEntities(true, -1, -1);
    }

    public List<TipoComida> findTipoComidaEntities(int maxResults, int firstResult) {
        return findTipoComidaEntities(false, maxResults, firstResult);
    }

    private List<TipoComida> findTipoComidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoComida.class));
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

    public TipoComida findTipoComida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoComida.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoComidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoComida> rt = cq.from(TipoComida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
