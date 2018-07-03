package com.itacademy.jd2.ai.b2b.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.ai.b2b.dao.api.IBrandDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.BrandFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.jdbc.exception.SQLExecutionException;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.Brand;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.Image;

@Repository
public class BrandDaoImpl extends AbstractDaoImpl<IBrand, Integer> implements IBrandDao {

    @Override
    public IBrand createEntity() {
        return new Brand();
    }

    @Override
    public void insert(final IBrand entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "insert into brand (name, description, created, updated, image_id) "
                                + "values(?,?,?,?,?)",
                        getTableName()), Statement.RETURN_GENERATED_KEYS)) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getDescription());
                pStmt.setObject(3, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(4, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(5, entity.getImage().getId());

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
    public void update(final IBrand entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "update brand set name=?, description=?, updated=? where id=?",
                        getTableName()))) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getDescription());
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
    protected IBrand parseRow(final ResultSet resultSet, final Set<String> columns)
            throws SQLException {
        final IBrand entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setDescription(resultSet.getString("description"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        final Integer imageId = (Integer) resultSet.getObject("image_id");
        if (imageId != null) {
            final Image image = new Image();
            image.setId(imageId);
            if (columns.contains("image_name")) {
                image.setName(resultSet.getString("image_name"));
            }

            entity.setImage(image);
        }
        return entity;
    }

    @Override
    protected String getTableName() {
        return "brand";
    }

    @Override
    public List<IBrand> find(final BrandFilter filter) {
        final StringBuilder sql;
        if (filter.getFetchImage()) {
            sql = new StringBuilder(String.format(
                    "select brand.*, image.name as image_name from %s", getTableName()));
        } else {
            sql = new StringBuilder(
                    String.format("select brand.* from %s", getTableName()));
        }
        appendJOINs(sql, filter);
        appendWHEREs(sql, filter);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQueryWithCustomSelect(sql.toString());
    }

    private void appendJOINs(final StringBuilder sb, final BrandFilter filter) {
        if (filter.getFetchImage()) {
            sb.append(" join image on (image.id=brand.image_id) ");
        }
    }

    private void appendWHEREs(final StringBuilder sb, final BrandFilter filter) {
        // nothing yet
    }

    @Override
    public long getCount(final BrandFilter filter) {
        return executeCountQuery("");
    }
}
