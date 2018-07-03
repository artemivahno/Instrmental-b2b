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

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.ai.b2b.dao.api.IOrderObjectDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderObjectFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.orm.model.OrderObject;
import com.itacademy.jd2.ai.b2b.dao.orm.model.OrderObject_;
import com.itacademy.jd2.ai.b2b.dao.orm.model.UserAccount_;

@Repository
public class OrderObjectDaoImpl extends AbstractDaoImpl<IOrderObject, Integer>
        implements IOrderObjectDao {

    protected OrderObjectDaoImpl() {
        super(OrderObject.class);
    }

    @Override
    public IOrderObject createEntity() {
        return new OrderObject();
    }

    @Override
    public IOrderObject getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<IOrderObject> cq = cb.createQuery(IOrderObject.class);
        final Root<OrderObject> from = cq.from(OrderObject.class);

        cq.select(from);

        from.fetch(OrderObject_.creator, JoinType.LEFT);

        cq.distinct(true); // to avoid duplicate rows in result

        // .. where id=...
        cq.where(cb.equal(from.get(OrderObject_.id), id));

        final TypedQuery<IOrderObject> q = em.createQuery(cq);

        return getSingleResult(q);
    }

    @Override
    public long getCount(final OrderObjectFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<OrderObject> from = cq.from(OrderObject.class);

        cq.select(cb.count(from));

        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    @Override
    public List<IOrderObject> find(final OrderObjectFilter filter) {

        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        // create empty query and define returning type
        final CriteriaQuery<IOrderObject> cq = cb.createQuery(IOrderObject.class);

        // define target entity(table)
        final Root<OrderObject> from = cq.from(OrderObject.class); // select
                                                                   // from model

        // define what will be added to result set
        cq.select(from); // select * from model
        
        final List<Predicate> ands = new ArrayList<>();

        final Integer userId = filter.getUserId();
        if (userId != null) {
            ands.add(cb.equal(from.get(OrderObject_.creator).get(UserAccount_.id), userId));
        }
        
        final Boolean prep = filter.getClose();
        if (prep != null) {
            ands.add(cb.equal(from.get(OrderObject_.close), prep));
        }
        
        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
        
        from.fetch(OrderObject_.creator, JoinType.LEFT);
        
        if (filter.isFetchOrderItem()) {
            from.fetch(OrderObject_.orderItems, JoinType.LEFT);
        }
        
        from.fetch(OrderObject_.creator, JoinType.LEFT);
        if (filter.getFetchUser()) {
            // select m, b from model m left join brand b ...
            from.fetch(OrderObject_.creator, JoinType.LEFT);
        }

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IOrderObject> q = em.createQuery(cq);
        setPaging(filter, q);
        final List<IOrderObject> resultList = q.getResultList();
        return resultList;
    }

    private Path<?> getSortPath(final Root<OrderObject> from, final String sortColumn) {
        switch (sortColumn) {
        case "close":
            return from.get(OrderObject_.close);
        case "created":
            return from.get(OrderObject_.created);
        case "updated":
            return from.get(OrderObject_.updated);
        case "id":
            return from.get(OrderObject_.id);
        case "brand":
            return from.get(OrderObject_.creator).get(UserAccount_.name);
        default:
            throw new UnsupportedOperationException(
                    "sorting is not supported by column:" + sortColumn);
        }
    }
}
