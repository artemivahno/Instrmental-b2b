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

import com.itacademy.jd2.ai.b2b.dao.api.ICategoryDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.CategoryFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Category;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Category_;
import com.itacademy.jd2.ai.b2b.dao.orm.model.Image_;

@Repository
public class CategoryDaoImpl extends AbstractDaoImpl<ICategory, Integer>
        implements ICategoryDao {

    protected CategoryDaoImpl() {
        super(Category.class);
    }

    @Override
    public ICategory createEntity() {
        return new Category();
    }

    @Override
    public ICategory getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<ICategory> cq = cb.createQuery(ICategory.class);
        final Root<Category> from = cq.from(Category.class);

        cq.select(from);

        from.fetch(Category_.image, JoinType.LEFT);

        cq.distinct(true); // to avoid duplicate rows in result

        // .. where id=...
        cq.where(cb.equal(from.get(Category_.id), id));

        final TypedQuery<ICategory> q = em.createQuery(cq);

        return getSingleResult(q);
    }

    @Override
    public List<ICategory> find(final CategoryFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        // create empty query and define returning type
        final CriteriaQuery<ICategory> cq = cb.createQuery(ICategory.class);

        // define target entity(table)
        final Root<Category> from = cq.from(Category.class); // select from
                                                             // model
        // define what will be added to result set
        cq.select(from); // select * from model


        if (filter.getFetchImage()) {
            // select m, b from model m left join brand b ...
            from.fetch(Category_.image, JoinType.LEFT);
        }

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<ICategory> q = em.createQuery(cq);
        setPaging(filter, q);
        final List<ICategory> resultList = q.getResultList();
        return resultList;
    }

    @Override
    public long getCount(final CategoryFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Category> from = cq.from(Category.class);

        cq.select(cb.count(from));

        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private Path<?> getSortPath(final Root<Category> from, final String sortColumn) {
        switch (sortColumn) {
        case "name":
            return from.get(Category_.name);
        case "description":
            return from.get(Category_.description);
        case "position":
            return from.get(Category_.position);
        case "created":
            return from.get(Category_.created);
        case "updated":
            return from.get(Category_.updated);
        case "id":
            return from.get(Category_.id);
        case "image":
            return from.get(Category_.image).get(Image_.name);
        default:
            throw new UnsupportedOperationException(
                    "sorting is not supported by column:" + sortColumn);
        }
    }

}
