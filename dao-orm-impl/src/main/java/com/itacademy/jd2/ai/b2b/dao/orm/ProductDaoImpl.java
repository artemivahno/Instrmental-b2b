package com.itacademy.jd2.ai.b2b.dao.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.ai.b2b.dao.api.IProductDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.ProductFilter;
import com.itacademy.jd2.ai.b2b.dao.api.filter.UserFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Brand_;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Category_;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Product;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Product_;
import com.itacademy.jd2.ai.b2b.dao.orm.model.UserAccount;
import com.itacademy.jd2.ai.b2b.dao.orm.model.UserAccount_;

@Repository
public class ProductDaoImpl extends AbstractDaoImpl<IProduct, Integer>
        implements IProductDao {

    protected ProductDaoImpl() {
        super(Product.class);
    }

    @Override
    public IProduct createEntity() {
        final Product product = new Product();
        product.setVisible(Boolean.TRUE);
        product.setVersion(IProduct.DEFAULT_VERSION);
        return product;
    }

    @Override
    public IProduct getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<IProduct> cq = cb.createQuery(IProduct.class);
        final Root<Product> from = cq.from(Product.class);
        cq.select(from);

        from.fetch(Product_.category, JoinType.LEFT);
        from.fetch(Product_.brand, JoinType.LEFT);
        from.fetch(Product_.images, JoinType.LEFT);

        cq.distinct(true); // to avoid duplicate rows in result

        // .. where id=...
        cq.where(cb.equal(from.get(Product_.id), id));

        final TypedQuery<IProduct> q = em.createQuery(cq);

        return getSingleResult(q);
    }

    private void applyFilter(final ProductFilter filter, final CriteriaBuilder cb,
            final CriteriaQuery<IProduct> cq, final Root<Product> from) {
        final List<Predicate> ands = new ArrayList<>();
        final String name = filter.getName();
        final Integer categoryId = filter.getCategoryId();
        if (!StringUtils.isBlank(name)) {
            ands.add(cb.like(from.get(Product_.name), "%" + name + "%"));
        }
        
        if (categoryId!=null) {
            ands.add(cb.equal(from.get(Product_.category).get(Category_.id), categoryId ));
        }
        
        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    @Override
    public List<IProduct> find(final ProductFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        // create empty query and define returning type
        final CriteriaQuery<IProduct> cq = cb.createQuery(IProduct.class);

        // define target entity(table)
        final Root<Product> from = cq.from(Product.class); // select from model

        // define what will be added to result set
        cq.select(from); // select * from model

        from.fetch(Product_.category, JoinType.LEFT);
        from.fetch(Product_.brand, JoinType.LEFT);
        from.fetch(Product_.images, JoinType.LEFT);
 
        applyFilter(filter, cb, cq, from);

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IProduct> q = em.createQuery(cq);
        setPaging(filter, q);
        final List<IProduct> resultList = q.getResultList();
        return resultList;
    }

    @Override
    public long getCount(final ProductFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Product> from = cq.from(Product.class);

        cq.select(cb.count(from));

        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private Path<?> getSortPath(final Root<Product> from, final String sortColumn) {
        switch (sortColumn) {
        case "name":
            return from.get(Product_.name);
        case "description":
            return from.get(Product_.description);
        case "external_link":
            return from.get(Product_.externalLink);
        case "visible":
            return from.get(Product_.visible);
        case "position":
            return from.get(Product_.position);
        case "price":
            return from.get(Product_.price);
        case "quantity_stock":
            return from.get(Product_.quantityStock);
        case "created":
            return from.get(Product_.created);
        case "updated":
            return from.get(Product_.updated);
        case "id":
            return from.get(Product_.id);
        case "brand":
            return from.get(Product_.brand).get(Brand_.name);
        case "category":
            return from.get(Product_.category).get(Category_.name);
        /*
         * case "images": return from.get(Product_.images).get(Image_.name);
         */
        case "version":
            return from.get(Product_.version);
        default:
            throw new UnsupportedOperationException(
                    "sorting is not supported by column:" + sortColumn);
        }
    }

}
