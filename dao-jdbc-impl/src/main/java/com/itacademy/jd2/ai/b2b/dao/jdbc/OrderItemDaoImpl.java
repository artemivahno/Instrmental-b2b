package com.itacademy.jd2.ai.b2b.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.itacademy.jd2.ai.b2b.dao.api.IOrderItemDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderItemFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.dao.jdbc.exception.SQLExecutionException;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.OrderItem;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.OrderObject;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.Product;

@Repository
public class OrderItemDaoImpl extends AbstractDaoImpl<IOrderItem, Integer>
        implements IOrderItemDao {

    @Override
    public IOrderItem createEntity() {
        return new OrderItem();
    }

    @Override
    public void insert(final IOrderItem entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c
                        .prepareStatement(String.format(
                                "insert into order_item (quantity, order_id, product_id, "
                                        + "created, updated) values(?,?,?,?,?)",
                                getTableName()), Statement.RETURN_GENERATED_KEYS)) {
            c.setAutoCommit(false);
            try {
                pStmt.setInt(1, entity.getQuantity());
                pStmt.setInt(2, entity.getOrderObject().getId());
                pStmt.setInt(3, entity.getProduct().getId());
                pStmt.setObject(4, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(5, entity.getUpdated(), Types.TIMESTAMP);

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
    public void update(final IOrderItem entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "update order_item set quantity=?, order_id=?, product_id=?, "
                                + "updated=? where id=?",
                        getTableName()))) {
            c.setAutoCommit(false);
            try {
                pStmt.setInt(1, entity.getQuantity());
                pStmt.setInt(2, entity.getOrderObject().getId());
                pStmt.setInt(3, entity.getProduct().getId());
                pStmt.setObject(4, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(5, entity.getId());

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
    protected IOrderItem parseRow(final ResultSet resultSet, final Set<String> columns)
            throws SQLException {
        final IOrderItem entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setQuantity(resultSet.getInt("quantity"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        final OrderObject order = new OrderObject();
        order.setId((Integer) resultSet.getObject("order_id"));
        entity.setOrderObject(order);
        final Product product = new Product();
        product.setId((Integer) resultSet.getObject("product_id"));
        entity.setProduct(product);
        return entity;
    }

    @Override
    protected String getTableName() {
        return "order_item";
    }
    // Думаю мне не нужен поист для этой таблицы

    @Override
    public long getCount(OrderItemFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        return executeCountQuery(sql.toString());

    }

    @Override
    public List<IOrderItem> find(OrderItemFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    private void appendWHEREs(final OrderItemFilter filter, final StringBuilder sb) {

        /*
         * final List<String> ands = new ArrayList<String>(); if
         * (StringUtils.isNotBlank(filter.getQuantity())) {
         * ands.add(String.format("quantity='%s'", filter.getQuantity())); //
         * SQL // injections??? }
         * 
         * 
         * final Iterator<String> andsIter = ands.iterator(); if
         * (andsIter.hasNext()) { final String firtsCondition = andsIter.next();
         * 
         * sb.append(String.format("where %s", firtsCondition));
         * 
         * while (andsIter.hasNext()) { final String condition =
         * andsIter.next(); sb.append(String.format(" and %s", condition)); }
         * 
         * }
         * 
         * }
         */
    }

    @Override
    public IOrderItem getFullInfo(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }
}