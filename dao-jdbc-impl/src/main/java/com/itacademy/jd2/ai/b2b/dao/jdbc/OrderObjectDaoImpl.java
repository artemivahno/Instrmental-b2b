package com.itacademy.jd2.ai.b2b.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.ai.b2b.dao.api.IOrderObjectDao;
import com.itacademy.jd2.ai.b2b.dao.api.IUserDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderObjectFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.jdbc.exception.SQLExecutionException;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.OrderObject;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.User;

@Repository
public class OrderObjectDaoImpl extends AbstractDaoImpl<IOrderObject, Integer>
        implements IOrderObjectDao {

    @Autowired
    private IUserDao userDao;

    @Override
    public IOrderObject createEntity() {
        return new OrderObject();
    }

    @Override
    public void insert(final IOrderObject entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "insert into order_object (prepayment, creator_id, created, updated)"
                                + " values(?,?,?,?)",
                        getTableName()), Statement.RETURN_GENERATED_KEYS)) {
            c.setAutoCommit(false);
            try {
                pStmt.setBoolean(1, entity.getClose());
                pStmt.setInt(2, entity.getCreator().getId());
                pStmt.setObject(3, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(4, entity.getUpdated(), Types.TIMESTAMP);

                pStmt.executeUpdate();

                final ResultSet rs = pStmt.getGeneratedKeys();
                rs.next();
                final int id = rs.getInt("id");

                rs.close();
                entity.setId(id);

                c.commit();
            } catch (final Exception e) {
                c.rollback();
                throw new RuntimeException(e);
            }

        } catch (final SQLException e) {
            throw new SQLExecutionException(e);
        }
    }

    @Override
    public void update(final IOrderObject entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String
                        .format("update order_object set prepayment=?, creator_id=?, "
                                + "updated=? where id=?", getTableName()))) {
            c.setAutoCommit(false);
            try {
                pStmt.setBoolean(1, entity.getClose());
                pStmt.setInt(2, entity.getCreator().getId());
                pStmt.setObject(3, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(4, entity.getId());

                pStmt.executeUpdate();

                c.commit();
            } catch (final Exception e) {
                c.rollback();
                throw new RuntimeException(e);
            }

        } catch (final SQLException e) {
            throw new SQLExecutionException(e);
        }

    }

    @Override
    protected IOrderObject parseRow(final ResultSet resultSet, final Set<String> columns)
            throws SQLException {
        final IOrderObject entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setClose(resultSet.getBoolean("close"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        final Integer userId = (Integer) resultSet.getObject("creator_id");
        if (userId != null) {
            final User user = new User();
            user.setId(userId);
            if (columns.contains("user_account_name")) {
                user.setName(resultSet.getString("user_account_name"));
            }
            entity.setCreator(user);
        }

        return entity;
    }

    @Override
    protected String getTableName() {
        return "order_object";
    }

    @Override
    public List<IOrderObject> find(final OrderObjectFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());

    }

    @Override
    public long getCount(final OrderObjectFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        return executeCountQuery(sql.toString());
    }

    private void appendWHEREs(final OrderObjectFilter filter, final StringBuilder sb) {
        // nothing yet
    }

    @Override
    public IOrderObject getFullInfo(final Integer id) {
        throw new UnsupportedOperationException();
    }
}
