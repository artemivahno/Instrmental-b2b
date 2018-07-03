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

import com.itacademy.jd2.ai.b2b.dao.api.IUserDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.UserFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.orm.model.UserAccount;
import com.itacademy.jd2.ai.b2b.dao.orm.model.UserAccount_;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<IUser, Integer> implements IUserDao {

    protected UserDaoImpl() {
        super(UserAccount.class);
    }

    @Override
    public IUser createEntity() {
        return new UserAccount();
    }

    @Override
    public long getCount(final UserFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<UserAccount> from = cq.from(UserAccount.class);
        cq.select(cb.count(from));
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    @Override
    public List<IUser> find(final UserFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUser> cq = cb.createQuery(IUser.class);
        final Root<UserAccount> from = cq.from(UserAccount.class);
        cq.select(from);

        applyFilter(filter, cb, cq, from);

        if (filter.getSortColumn() != null) {
            final SingularAttribute<? super UserAccount, ?> sortProperty = toMetamodelFormat(
                    filter.getSortColumn());
            final Path<?> expression = from.get(sortProperty);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IUser> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    private void applyFilter(final UserFilter filter, final CriteriaBuilder cb,
            final CriteriaQuery<IUser> cq, final Root<UserAccount> from) {
        final List<Predicate> ands = new ArrayList<>();
        final String name = filter.getName();
        if (!StringUtils.isBlank(name)) {
            ands.add(cb.like(from.get(UserAccount_.name), "%" + name + "%"));
        }
        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }

    }

    private SingularAttribute<? super UserAccount, ?> toMetamodelFormat(
            final String sortColumn) {
        switch (sortColumn) {
        case "created":
            return UserAccount_.created;
        case "updated":
            return UserAccount_.updated;
        case "id":
            return UserAccount_.id;
        case "name":
            return UserAccount_.name;
        case "password":
            return UserAccount_.password;
        case "role":
            return UserAccount_.role;
        case "email":
            return UserAccount_.email;
        case "enabled":
            return UserAccount_.enabled;
        default:
            throw new UnsupportedOperationException(
                    "sorting is not supported by column:" + sortColumn);
        }
    }

    @Override
    public IUser enterEmail(final String email) {

        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUser> cq = cb.createQuery(IUser.class);
        final Root<UserAccount> from = cq.from(UserAccount.class);
        cq.select(from);
        cq.where(cb.equal(from.get(UserAccount_.email), email));
        final TypedQuery<IUser> q = em.createQuery(cq);

        final IUser singleResult = q.getSingleResult();
        return singleResult;

    }

    @Override
    public IUser getByEmail(final String login) {

        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUser> cq = cb.createQuery(IUser.class);
        final Root<UserAccount> from = cq.from(UserAccount.class);
        cq.select(from);
        cq.where(cb.equal(from.get(UserAccount_.email), login));
        final TypedQuery<IUser> q = em.createQuery(cq);

        final List<IUser> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

}
