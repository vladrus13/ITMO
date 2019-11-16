package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.service.UserService;

import javax.sql.DataSource;
import java.sql.*;

public class XRepository<T> {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    public PreparedStatement connect(String command, boolean returned) throws SQLException{
        Connection connection = DATA_SOURCE.getConnection();
        return (returned ?
                connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS) :
                connection.prepareStatement(command));
    }

    public Object[] findResultSet(long id, String nameClass) throws SQLException {
        PreparedStatement statement = connect(makeResponceSelect(nameClass, new String[]{"id"}), false);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        return new Object[]{statement.getMetaData(), resultSet};
    }

    public String makeResponceSelect(String from, String[] where) {
        StringBuilder returned = new StringBuilder(String.format("SELECT * FROM %s", from));
        if (where.length > 0) {
            returned.append(" WHERE ");
            for (String i : where) {
                returned.append(i).append("=? ");
            }
            return returned.toString();
        } else {
            return returned.toString();
        }
    }

    public PreparedStatement getReadyStatement(String s, Object[] setters, boolean isReturn) throws SQLException {
        PreparedStatement statement = connect(s, isReturn);
        for (int i = 0; i < setters.length; i++) {
            Object it = setters[i];
            if (it instanceof String) {
                statement.setString(i + 1, (String) it);
            } else {
                statement.setLong(i + 1, (long) it);
            }
        }
        return statement;
    }
}
