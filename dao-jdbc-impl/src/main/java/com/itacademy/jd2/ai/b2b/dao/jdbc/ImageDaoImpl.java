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

import com.itacademy.jd2.ai.b2b.dao.api.IImageDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.ImageFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.dao.jdbc.exception.SQLExecutionException;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.Image;

@Repository
public class ImageDaoImpl extends AbstractDaoImpl<IImage, Integer> implements IImageDao {

    @Override
    public IImage createEntity() {
        return new Image();
    }

    @Override
    public void insert(final IImage entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "insert into image (name, url, position, created, updated) "
                                + "values(?,?,?,?,?)",
                        getTableName()), Statement.RETURN_GENERATED_KEYS)) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getUrl());
                pStmt.setInt(3, entity.getPosition());
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
    public void update(final IImage entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "update image set name=?, url=?, position=?, updated=? where id=?",
                        getTableName()))) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getUrl());
                pStmt.setInt(3, entity.getPosition());
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
    protected String getTableName() {
        return "image";
    }

    @Override
    protected IImage parseRow(final ResultSet resultSet, final Set<String> columns)
            throws SQLException {
        final IImage entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setUrl(resultSet.getString("url"));
        entity.setPosition(resultSet.getInt("position"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    public long getCount(final ImageFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        return executeCountQuery(sql.toString());

    }

    @Override
    public List<IImage> find(final ImageFilter filter) {

        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    private void appendWHEREs(final ImageFilter filter, final StringBuilder sb) {

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
