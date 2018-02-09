package edu.acc.j2ee.hubbub;

import java.io.Serializable;
import java.time.LocalDate;
import edu.acc.j2ee.validation.*;

public class User extends ValidatedDomain implements Serializable {
    public static String USER_PATTERN = "\\w{6,12}";
    
    private String username;
    private String password;
    private LocalDate joined;

    public User() {
    }

    public User(String username, String password, LocalDate joined) {
        this.username = username;
        this.password = password;
        this.joined  = joined;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getJoined() {
        return joined;
    }

    public void setJoined(LocalDate joined) {
        this.joined = joined;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password +
                ", joined=" + joined + '}';
    }

    @Override
    protected void validate() throws ValidationException {
        if (!username.matches(USER_PATTERN))
            addError("username", username, "must be 6-12 characters including letters, numbers, and underscores");
        if (!password.matches(".{8,20}"))
            addError("password", password, "must be 8-20 characters long");
        boolean hasUpperCase = false, hasLowerCase = false, hasPunctuation = false,
                hasDigit = false, hasInjection = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) hasDigit = true;
            if (Character.isUpperCase(c)) hasUpperCase = true;
            if (Character.isLowerCase(c)) hasLowerCase = true;
            if ("~!@#$%^&*()_+-=|{}[],.?/\\:;".indexOf(c) >= 0) hasPunctuation = true;
            if ("<>'\"`".indexOf(c) >= 0) hasInjection = true;
        }
        if (!hasUpperCase)
            addError("password", password, "must contain an uppercase character");
        if (!hasLowerCase)
            addError("password", password, "must contain a lowercase character");
        if (!hasDigit)
            addError("password", password, "must contain a digit");
        if (!hasPunctuation)
            addError("password", password, "must contain a punctuation character");
        if (hasInjection)
            addError("password", password, "cannot contain < > ' \" or `");
        if (hasErrors()) throw new ValidationException(getErrors());
    }  
}
