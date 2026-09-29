package internship.utils;

import internship.constants.Queries;
import internship.dto.OrderStatusDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static internship.constants.ConnectionParams.*;

public class DBHelper {

    private final Connection connection;

    public DBHelper(int postgresMappedPort) {
        try {
            this.connection = DriverManager.getConnection(
                    String.format(DB_URI, postgresMappedPort),
                    LOGIN,
                    PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DBHelper(String uri, String login, String password) {
        try {
            this.connection = DriverManager.getConnection(uri, login, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.CREATE_USERS_TABLE)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createOrdersTable() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.CREATE_ORDERS_TABLE)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createOrdersTableForMQTest() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.CREATE_ORDERS_TABLE_FOR_MQ_TEST)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropOrdersTable() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.DROP_ORDERS_TABLE)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createOrder(int id, String status) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.INSERT_ORDER)) {
            statement.setInt(1, id);
            statement.setString(2, status);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countOrders(int orderId) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.COUNT_ORDERS)) {
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertTestUsers() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.INSERT_TEST_USERS_QUERY)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertTestOrders() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.INSERT_TEST_ORDERS)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersAndOrders() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.CLEAN_USERS_AND_ORDERS)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findUserByMail(String mail) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.FIND_USER_BY_MAIL)) {
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            int amountOfUsers = 0;
            if (resultSet.next()) {
                amountOfUsers = resultSet.getInt("count");
            }
            return amountOfUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findActiveUsersForTheLastWeek() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.FIND_ACTIVE_USERS_FOR_THE_LAST_WEEK)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int amountOfUsers = 0;
            if (resultSet.next()) {
                amountOfUsers = resultSet.getInt("count");
            }
            return amountOfUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findUsersWithSpecificAmountOfOrders() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.FIND_USERS_WITH_SPECIFIC_AMOUNT_OF_ORDERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int amountOfUsers = 0;
            if (resultSet.next()) {
                amountOfUsers = resultSet.getInt("count");
            }
            return amountOfUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUserAndOrder() {
        try {
            try {
                connection.setAutoCommit(false);
                int userId;
                try (PreparedStatement ps = connection.prepareStatement(Queries.INSERT_USER_AND_RETURN_ID)) {
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        userId = rs.getInt("id");
                    }
                }
                try (PreparedStatement ps = connection.prepareStatement(Queries.INSERT_ORDER_FOR_USER)) {
                    ps.setInt(1, userId);
                    ps.execute();
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkIfCreatedUserExists() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.CHECK_IF_NEW_USER_EXISTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int amountOfUsers = 0;
            if (resultSet.next()) {
                amountOfUsers = resultSet.getInt("count");
            }
            return amountOfUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderStatus(String email) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_ORDER_STATUS)) {
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderStatusDTO> checkIfOrderStatusChanged() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.CHECK_IF_ORDER_STATUS_CHANGED)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OrderStatusDTO> updatedOrders = new ArrayList<>();
            if (resultSet.next()) {
                String orderStatus = resultSet.getString("status");
                int amountOfOrders = resultSet.getInt("count");
                OrderStatusDTO orderStatusDTO = new OrderStatusDTO(orderStatus, amountOfOrders);
                updatedOrders.add(orderStatusDTO);
            }
            return updatedOrders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String insertNewUser() {
        try {
            int id = -1;
            try (PreparedStatement statement = connection.prepareStatement(Queries.INSERT_NEW_USER)) {
                //'John Doe', 'john_doe@gmail.com', 'active'
                statement.setString(1, "John Doe");
                statement.setString(2, "john_doe@gmail.com");
                statement.setString(3, "active");
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(Queries.FIND_CREATED_USER)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void createEventsTable() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.CREATE_EVENTS_TABLE)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropEventsTable() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.DROP_EVENTS_TABLE)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String checkEventsTable(String requestID) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.FIND_EVENT_WITH_ID)) {
            String status = null;
            statement.setString(1, requestID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                status = resultSet.getString("status");
            }
            return status;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
