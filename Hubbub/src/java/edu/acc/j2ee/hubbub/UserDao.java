package edu.acc.j2ee.hubbub;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final List<User> users = new ArrayList<>();

    public UserDao() {}
    
    public User register(User user) {
        for (User u : users)
            if (u.getUsername().equals(user.getUsername()))
                return null;
        User newUser = new User(user.getUsername(), user.getPassword(), LocalDate.now());
        users.add(newUser);
        return newUser;
    }
    
    public User authenticate(User user) {
        for (User u : users)
            if (u.getUsername().equals(user.getUsername()) &&
                    u.getPassword().equals(user.getPassword()))
                return u;
        return null;
    }
}
