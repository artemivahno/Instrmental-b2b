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

import com.itacademy.jd2.ai.b2b.dao.api.IProductDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.ProductFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.dao.jdbc.exception.SQLExecutionException;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.Brand;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.Category;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.Product;

@Repository
public class ProductDaoImpl extends AbstractDaoImpl<IProduct, Integer>
        implements IProductDao {

    @Override
    public IProduct createEntity() {
        return new Product();
    }

    @Override
    public void insert(final IProduct entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "insert into product (name, description, external_link, visible, "
                                + "position, price, quantity_stock, created, updated, "
                                + "category_id, brand_id) "
                                + "values(?,?,?,?,?,?,?,?,?,?,?)",
                        getTableName()), Statement.RETURN_GENERATED_KEYS)) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getDescription());
                pStmt.setString(3, entity.getExternalLink());
                pStmt.setBoolean(4, entity.getVisible());
                pStmt.setInt(5, entity.getPosition());
                pStmt.setDouble(6, entity.getPrice());
                pStmt.setInt(7, entity.getQuantityStock());
                pStmt.setObject(8, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(9, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(10, entity.getCategory().getId());
                pStmt.setInt(11, entity.getBrand().getId());

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
    public void update(final IProduct entity) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = c.prepareStatement(String.format(
                        "update product set name=?, description=?, external_link=?, visible=?,"
                                + "position=?, price=?, quantity_stock=?,"
                                + "updated=?, category_id=?, brand_id=?  where id=?",
                        getTableName()))) {
            c.setAutoCommit(false);
            try {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getDescription());
                pStmt.setString(3, entity.getExternalLink());
                pStmt.setBoolean(4, entity.getVisible());
                pStmt.setInt(5, entity.getPosition());
                pStmt.setDouble(6, entity.getPrice());
                pStmt.setInt(7, entity.getQuantityStock());
                pStmt.setObject(8, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(9, entity.getCategory().getId());
                pStmt.setInt(10, entity.getBrand().getId());
                pStmt.setInt(11, entity.getId());

                pStmt.executeUpdate();

            } catch (final Exception e) {
                c.rollback();
                throw new RuntimeException(e);
            }

        } catch (final SQLException e) {
            throw new SQLExecutionException(e);
        }

    }

    @Override
    protected IProduct parseRow(final ResultSet resultSet) throws SQLException {
        final IProduct entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setDescription(resultSet.getString("description"));
        entity.setExternalLink(resultSet.getString("external_link"));
        entity.setVisible(resultSet.getBoolean("visible"));
        entity.setPosition(resultSet.getInt("position"));
        entity.setPrice(resultSet.getDouble("price"));
        entity.setQuantityStock(resultSet.getInt("quantity_stock"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        final Category category = new Category();
        category.setId((Integer) resultSet.getObject("category_id"));
        entity.setCategory(category);
        final Brand brand = new Brand();
        brand.setId((Integer) resultSet.getObject("brand_id"));
        entity.setBrand(brand);

        return entity;
    }

    protected IProduct parseRow(final ResultSet resultSet, final Set<String> columns)
            throws SQLException {
        final IProduct entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setDescription(resultSet.getString("description"));
        entity.setExternalLink(resultSet.getString("external_link"));
        entity.setVisible(resultSet.getBoolean("visible"));
        entity.setPosition(resultSet.getInt("position"));
        entity.setPrice(resultSet.getDouble("price"));
        entity.setQuantityStock(resultSet.getInt("quantity_stock"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        final Integer categoryId = (Integer) resultSet.getObject("category_id");
        if (categoryId != null) {
            final Category category = new Category();
            category.setId(categoryId);
            if (columns.contains("category_name")) {
                category.setName(resultSet.getString("category_name"));
            }
            entity.setCategory(category);
        }

        final Integer brandId = (Integer) resultSet.getObject("brand_id");
        if (brandId != null) {
            final Brand brand = new Brand();
            brand.setId(brandId);
            if (columns.contains("brand_name")) {
                brand.setName(resultSet.getString("brand_name"));
            }
            entity.setBrand(brand);
        }

        return entity;
    }

    @Override
    protected String getTableName() {
        return "product";
    }

    @Override
    public List<IProduct> find(ProductFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(sql, filter);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    private void appendWHEREs(final StringBuilder sb, final ProductFilter filter) {
        final List<String> ands = new ArrayList<String>();
        if (StringUtils.isNotBlank(filter.getName())) {
            ands.add(String.format("Name='%s'", filter.getName())); // SQL
                                                                    // injections???
        }

        if (filter.getVisible() != null) {
            ands.add(String.format("Visible='%s'", filter.getVisible()));
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

    @Override
    public long getCount(final ProductFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(sql, filter);
        return executeCountQuery(sql.toString());
    }

    @Override
    public IProduct getFullInfo(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

}
