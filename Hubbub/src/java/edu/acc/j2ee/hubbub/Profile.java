package edu.acc.j2ee.hubbub;

import edu.acc.j2ee.validation.ValidatedDomain;
import edu.acc.j2ee.validation.ValidationException;

public class Profile extends ValidatedDomain implements java.io.Serializable {
    private User owner;
    private String firstName;
    private String lastName;
    private String email;
    private String biography;
    private int id;

    public Profile() {
    }

    public Profile(User owner, String firstName, String lastName, String email, String biography, int id) {
        this.owner = owner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.biography = biography;
        this.id = id;
    }
    
    public User getOwner() { 
        return owner;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Profile{" + "owner=" + owner + ", firstName=" + firstName +
                ", lastName=" + lastName + ", email=" + email + ", biography="
                + biography + ", id=" + id + '}';
    }


    @Override
    protected void validate() throws ValidationException {
        if (firstName != null && firstName.length() > 0 ) {
            if (firstName.length() < 1 || firstName.length() > 20)
                addError("firstName", firstName, "Must be 1-20 characters long");
            if (!firstName.matches("[A-Z][a-z]*( ?[A-Z][a-z]*)?"))
                addError("firstName", firstName, "Begin names with uppercase letters - two names at most");
        }
        if (lastName != null && lastName.length() > 0) {
            if (lastName.length() < 1 || lastName.length() > 30) 
                addError("lastName", lastName, "Must be 1-30 characters long");
            if (!lastName.matches("[A-Z][a-z]*(( |-)?[A-Z][a-z]*)?"))
                addError("lastName", lastName, "Begin names with uppercase letters - two names at most");
        }
        if (email != null && email.length() > 0) {
            if (email.length() > 100)
                addError("email", email, "Must be less than 100 characters");
            if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}"))
                addError("email", email, "Must have an @ and a dotted domain");
        }
        if (biography != null && biography.length() > 0)
            biography = biography
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("'", "&apos;")
                    .replace("\"", "&quo;");
        if (hasErrors()) throw new ValidationException(getErrors());
    }
}
