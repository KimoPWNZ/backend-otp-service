package dao;

import com.promoit.otp.model.User;

import java.sql.*;
import java.util.Optional;

public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<User> findByLogin(String login) throws SQLException {
        String sql = "SELECT * FROM users WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setLogin(rs.getString("login"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setTelegramChatId(rs.getString("telegram_chat_id"));
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
    // ... другие методы: save, delete, findAll, findNonAdmins и т.д.
}