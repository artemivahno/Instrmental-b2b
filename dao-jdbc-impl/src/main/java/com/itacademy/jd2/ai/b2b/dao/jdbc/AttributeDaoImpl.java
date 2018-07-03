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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.ai.b2b.dao.api.IAttributeDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.AttributeFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;
import com.itacademy.jd2.ai.b2b.dao.jdbc.exception.SQLExecutionException;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.Attribute;

@Repository
public class AttributeDaoImpl extends AbstractDaoImpl<IAttribute, Integer>
        implements IAttributeDao {

    @Override
    public IAttribute createEntity() {
        return new Attribute();
    }

    @Override
    public void insert(final IAttribute entity) {

        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "insert into attribute (name, value, created, updated) values(?,?,?,?)",
                        getTableName()), Statement.RETURN_GENERATED_KEYS)) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getValue());
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
    public void update(final IAttribute entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "update attribute set name=?, value=?, updated=? where id=?",
                        getTableName()))) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getValue());
                pStmt.setObject(3, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(4, entity.getId());
                pStmt.executeUpdate();
                // the same should be done in 'update' DAO method
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
    protected String getTableName() {
        return "attribute";
    }

    @Override
    protected IAttribute parseRow(final ResultSet resultSet, final Set<String> columns)
            throws SQLException {
        final IAttribute entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setValue(resultSet.getString("value"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));
        return entity;
    }

    @Override
    public long getCount(final AttributeFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        return executeCountQuery(sql.toString());

    }

    @Override
    public List<IAttribute> find(final AttributeFilter filter) {

        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    private void appendWHEREs(final AttributeFilter filter, final StringBuilder sb) {

        final List<String> ands = new ArrayList<String>();
        if (StringUtils.isNotBlank(filter.getName())) {
            ands.add(String.format("name='%s'", filter.getName())); // SQL
                                                                    // injections???
        }

        final Iterator<String> andsIter = ands.iterator();
        if (andsIter.hasNext()) {
            final String firtsCondition = andsIter.next();

            sb.append(String.format("where %s", firtsCondition));

            while (andsIter.hasNext()) {
                final String condition = andsIter.next();
                sb.append(String.format(" and %s", condition));
            }

        }

    }

}
