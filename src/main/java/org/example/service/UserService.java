package org.example.service;

import org.example.dao.UserDAO;
import org.example.entity.User;
import org.example.entity.User;

import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public void addUser(User user) {
        userDAO.saveUser(user);
    }
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }
    public boolean deleteUserById(int id) {
        return userDAO.deleteUserById(id);
    }
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}
