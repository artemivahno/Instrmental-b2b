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

import com.itacademy.jd2.ai.b2b.dao.api.ICategoryDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.CategoryFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.jdbc.exception.SQLExecutionException;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.Category;

@Repository
public class CategoryDaoImpl extends AbstractDaoImpl<ICategory, Integer>
        implements ICategoryDao {

    @Override
    public ICategory createEntity() {
        return new Category();
    }

    @Override
    public void insert(final ICategory entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "insert into category (name, description, position, created, \"\r\n"
                                + "                        + \"updated, parent_id, image_id) values(?,?,?,?,?,?)",
                        getTableName()), Statement.RETURN_GENERATED_KEYS)) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getDescription());
                pStmt.setInt(3, entity.getPosition());
                pStmt.setObject(4, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(5, entity.getUpdated(), Types.TIMESTAMP);

                pStmt.setInt(6, entity.getImage().getId());

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
    public void update(final ICategory entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "update category set name=?, description=?, position=?, \"\r\n"
                                + "                        + \"updated=? where id=?",
                        getTableName()))) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getDescription());
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
    protected ICategory parseRow(final ResultSet resultSet, final Set<String> columns)
            throws SQLException {
        final ICategory entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setDescription(resultSet.getString("description"));
        entity.setPosition(resultSet.getInt("position"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        final Integer categoryId1 = (Integer) resultSet.getObject("image_id");
        if (categoryId1 != null) {
            final Category category = new Category();
            category.setId(categoryId1);
            if (columns.contains("image_name")) {
                category.setName(resultSet.getString("image_name"));
            }
        }

        return entity;
    }

    @Override
    protected String getTableName() {
        return "category";
    }

    @Override
    public List<ICategory> find(final CategoryFilter filter) {
        final StringBuilder sql;
        if (filter.getFetchImage()) {
            sql = new StringBuilder(
                    String.format("select category.*, image.name as image_name from %s",
                            getTableName()));
        } else {
            sql = new StringBuilder(
                    String.format("select category.* from %s", getTableName()));
        }
        appendWHEREs(filter, sql);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQueryWithCustomSelect(sql.toString());
    }

    @Override
    public long getCount(final CategoryFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        return executeCountQuery(sql.toString());
    }

    private void appendWHEREs(final CategoryFilter filter, final StringBuilder sb) {
    }

    @Override
    public ICategory getFullInfo(Integer id) {
        return null;
    }

}
