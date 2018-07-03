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

import com.itacademy.jd2.ai.b2b.dao.api.IOrderItemDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderItemFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.dao.orm.model.OrderItem;
import com.itacademy.jd2.ai.b2b.dao.orm.model.OrderItem_;
import com.itacademy.jd2.ai.b2b.dao.orm.model.OrderObject_;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Product_;

@Repository
public class OrderItemDaoImpl extends AbstractDaoImpl<IOrderItem, Integer>
        implements IOrderItemDao {

    protected OrderItemDaoImpl() {
        super(OrderItem.class);
    }

    @Override
    public IOrderItem createEntity() {
        return new OrderItem();
    }

    @Override
    public IOrderItem getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<IOrderItem> cq = cb.createQuery(IOrderItem.class);
        final Root<OrderItem> from = cq.from(OrderItem.class);

        cq.select(from);

        from.fetch(OrderItem_.orderObject, JoinType.LEFT);
        from.fetch(OrderItem_.product, JoinType.LEFT);

        cq.distinct(true); // to avoid duplicate rows in result

        // .. where id=...
        cq.where(cb.equal(from.get(OrderItem_.id), id));

        final TypedQuery<IOrderItem> q = em.createQuery(cq);

        return getSingleResult(q);
    }

    @Override
    public List<IOrderItem> find(final OrderItemFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        // create empty query and define returning type
        final CriteriaQuery<IOrderItem> cq = cb.createQuery(IOrderItem.class);

        // define target entity(table)
        final Root<OrderItem> from = cq.from(OrderItem.class); // select from
                                                               // model

        // define what will be added to result set
        cq.select(from); // select * from model

        if (filter.getFetchOrderObject()) {
            // select m, b from model m left join brand b ...
            from.fetch(OrderItem_.orderObject, JoinType.LEFT);
        }

        if (filter.getFetchProduct()) {
            // select m, b from model m left join brand b ...
            from.fetch(OrderItem_.product, JoinType.LEFT);
        }

        // .. where id=...
        Integer orderId = filter.getOrderId();
        cq.where(
                cb.equal(from.get(OrderItem_.orderObject).get(OrderObject_.id), orderId));

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IOrderItem> q = em.createQuery(cq);
        setPaging(filter, q);
        final List<IOrderItem> resultList = q.getResultList();
        return resultList;
    }

    @Override
    public long getCount(final OrderItemFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<OrderItem> from = cq.from(OrderItem.class);

        cq.select(cb.count(from));

        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private Path<?> getSortPath(final Root<OrderItem> from, final String sortColumn) {
        switch (sortColumn) {
        case "name":
            return from.get(OrderItem_.quantity);
        case "created":
            return from.get(OrderItem_.created);
        case "updated":
            return from.get(OrderItem_.updated);
        case "id":
            return from.get(OrderItem_.id);
        case "order":
            return from.get(OrderItem_.orderObject).get(OrderObject_.id);
        case "product":
            return from.get(OrderItem_.product).get(Product_.name);
        default:
            throw new UnsupportedOperationException(
                    "sorting is not supported by column:" + sortColumn);
        }
    }

}
