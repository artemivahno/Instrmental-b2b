package com.itacademy.jd2.ai.b2b.dao.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.ai.b2b.dao.api.IImageDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.ImageFilter;
import com.itacademy.jd2.ai.b2b.dao.api.filter.ProductFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Image;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Image_;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Product;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Product_;

@Repository
public class ImageDaoImpl extends AbstractDaoImpl<IImage, Integer> implements IImageDao {

    protected ImageDaoImpl() {
        super(Image.class);
    }

    @Override
    public IImage createEntity() {
        return new Image();
    }

    @Override
    public long getCount(final ImageFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Image> from = cq.from(Image.class);
        cq.select(cb.count(from));
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final ImageFilter filter, final CriteriaBuilder cb,
            final CriteriaQuery<IImage> cq, final Root<Image> from) {
        final List<Predicate> ands = new ArrayList<>();
        final String name = filter.getName();
        if (!StringUtils.isBlank(name)) {
            ands.add(cb.like(from.get(Image_.name), "%" + name + "%"));
        }
        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }
    
    @Override
    public List<IImage> find(final ImageFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IImage> cq = cb.createQuery(IImage.class);
        final Root<Image> from = cq.from(Image.class);
        cq.select(from);
        
        applyFilter(filter, cb, cq, from);

        if (filter.getSortColumn() != null) {
            final SingularAttribute<? super Image, ?> sortProperty = toMetamodelFormat(
                    filter.getSortColumn());
            final Path<?> expression = from.get(sortProperty);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IImage> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    private SingularAttribute<? super Image, ?> toMetamodelFormat(
            final String sortColumn) {
        switch (sortColumn) {
        case "created":
            return Image_.created;
        case "updated":
            return Image_.updated;
        case "id":
            return Image_.id;
        case "name":
            return Image_.name;
        case "url":
            return Image_.url;
        case "position":
            return Image_.position;
        default:
            throw new UnsupportedOperationException(
                    "sorting is not supported by column:" + sortColumn);
        }
    }

}
