package org.example;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandler {

    public boolean registerUser(String name, String email, String password) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        try (Connection connection = DatabaseConnection.connection()){
            String query = "INSERT INTO users (name, email, hashedPassword) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3, hashedPassword);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public User loginUser(String email, String password){
        try ( Connection connection = DatabaseConnection.connection()) {
            String query = "SELECT * FROM users  WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                String hash = resultSet.getString("hashedPassword");
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
                if (result.verified) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    return user;
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return null;
    }



    public boolean deleteUser(int userId){
        try (Connection connection = DatabaseConnection.connection()) {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
