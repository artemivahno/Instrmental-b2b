package com.itacademy.jd2.ai.b2b.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.ai.b2b.dao.api.IUserDao;
import com.itacademy.jd2.ai.b2b.dao.api.filter.UserFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;
import com.itacademy.jd2.ai.b2b.dao.jdbc.model.User;
import com.itacademy.jd2.ai.b2b.dao.jdbc.utils.PreparedStatemenAction;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<IUser, Integer> implements IUserDao {

    @Override
    public IUser createEntity() {
        return new User();
    }

    @Override
    public void insert(final IUser entity) {
        executeStatement(new PreparedStatemenAction<IUser>(
                "insert into user_account (name, email, password, enabled, "
                        + "role, created, updated) values(?,?,?,?,?,?,?)",
                true) {
            @Override
            public IUser doWithPreparedStatement(final PreparedStatement pStmt)
                    throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getEmail());
                pStmt.setString(3, entity.getPassword());
                pStmt.setBoolean(4, entity.getEnabled());
                pStmt.setString(5, entity.getRole().name());
                pStmt.setObject(6, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(7, entity.getUpdated(), Types.TIMESTAMP);

                pStmt.executeUpdate();

                final ResultSet rs = pStmt.getGeneratedKeys();
                rs.next();
                final int id = rs.getInt("id");

                rs.close();

                entity.setId(id);
                return entity;
            }
        });

    }

    @Override
    public void update(final IUser entity) {
        executeStatement(new PreparedStatemenAction<IUser>(
                "update user_account set name=?, email=?, password=?, enabled=?, "
                        + "role=?, updated=? where id=?") {
            @Override
            public IUser doWithPreparedStatement(final PreparedStatement pStmt)
                    throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getEmail());
                pStmt.setString(3, entity.getPassword());
                pStmt.setBoolean(4, entity.getEnabled());
                pStmt.setString(5, entity.getRole().name());
                pStmt.setObject(6, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(7, entity.getId());

                final int i = pStmt.executeUpdate();
                if (i == 1) {
                    return entity;
                }
                throw new RuntimeException("Wrong Insert");

            }
        });
    }

    @Override
    protected String getTableName() {
        return "user_account";
    }

    @Override
    protected IUser parseRow(final ResultSet resultSet) throws SQLException {
        final IUser entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setEmail(resultSet.getString("email"));
        entity.setPassword(resultSet.getString("password"));
        entity.setEnabled(resultSet.getBoolean("enabled"));
        entity.setRole(UserRole.valueOf(resultSet.getString("role")));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));
        return entity;
    }

    @Override
    public long getCount(final UserFilter filter) {
        return executeCountQuery("");
    }

    @Override
    public List<IUser> find(final UserFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(filter, sql);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    private void appendWHEREs(final UserFilter filter, final StringBuilder sb) {

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

    @Override
    public IUser enterEmail(String mail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IUser getByEmail(String login) {
        return null;
    }

}
