package com.itacademy.jd2.ai.b2b.dao.orm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.ai.b2b.dao.api.IAttributeDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.AttributeFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Attribute;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Attribute_;

@Repository
public class AttributeDaoImpl extends AbstractDaoImpl<IAttribute, Integer>
        implements IAttributeDao {

    protected AttributeDaoImpl() {
        super(Attribute.class);
    }

    @Override
    public IAttribute createEntity() {
        return new Attribute();
    }

    @Override
    public long getCount(final AttributeFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Attribute> from = cq.from(Attribute.class);
        cq.select(cb.count(from));
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    @Override
    public List<IAttribute> find(final AttributeFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IAttribute> cq = cb.createQuery(IAttribute.class);
        final Root<Attribute> from = cq.from(Attribute.class);
        cq.select(from);

        if (filter.getSortColumn() != null) {
            final SingularAttribute<? super Attribute, ?> sortProperty = toMetamodelFormat(
                    filter.getSortColumn());
            final Path<?> expression = from.get(sortProperty);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IAttribute> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    private SingularAttribute<? super Attribute, ?> toMetamodelFormat(
            final String sortColumn) {
        switch (sortColumn) {
        case "created":
            return Attribute_.created;
        case "updated":
            return Attribute_.updated;
        case "id":
            return Attribute_.id;
        case "name":
            return Attribute_.name;
        case "value":
            return Attribute_.value;
        default:
            throw new UnsupportedOperationException(
                    "sorting is not supported by column:" + sortColumn);
        }
    }

}
