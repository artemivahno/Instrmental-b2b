package com.itacademy.jd2.ai.b2b.dao.orm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.ai.b2b.dao.api.IBrandDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.BrandFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Brand;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Brand_;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Image_;

@Repository
public class BrandDaoImpl extends AbstractDaoImpl<IBrand, Integer> implements IBrandDao {

    protected BrandDaoImpl() {
        super(Brand.class);
    }

    @Override
    public IBrand createEntity() {
        return new Brand();
    }

    @Override
    public long getCount(final BrandFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Brand> from = cq.from(Brand.class);
        cq.select(cb.count(from));
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    @Override
    public List<IBrand> find(final BrandFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBrand> cq = cb.createQuery(IBrand.class);
        final Root<Brand> from = cq.from(Brand.class);
        cq.select(from);

        if (filter.getFetchImage()) {
            // select m, b from model m left join brand b ...
            from.fetch(Brand_.image, JoinType.LEFT);
        }

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IBrand> q = em.createQuery(cq);
        setPaging(filter, q);
        final List<IBrand> resultList = q.getResultList();
        return resultList;
    }

    private Path<?> getSortPath(final Root<Brand> from, final String sortColumn) {
        switch (sortColumn) {
        case "created":
            return from.get(Brand_.created);
        case "updated":
            return from.get(Brand_.updated);
        case "id":
            return from.get(Brand_.id);
        case "name":
            return from.get(Brand_.name);
        case "description":
            return from.get(Brand_.description);
        case "image":
            return from.get(Brand_.image).get(Image_.name);
        default:
            throw new UnsupportedOperationException(
                    "sorting is not supported by column:" + sortColumn);
        }
    }

}
