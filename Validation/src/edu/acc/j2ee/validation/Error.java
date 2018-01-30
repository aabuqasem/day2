package edu.acc.j2ee.validation;

import java.io.Serializable;

public class Error implements Serializable {
    private String offendingValue;
    private String offense;

    public Error() {
    }

    public Error(String offendingValue, String offense) {
        this.offendingValue = offendingValue;
        this.offense = offense;
    }

    public String getOffendingValue() {
        return offendingValue;
    }

    public void setOffendingValue(String offendingValue) {
        this.offendingValue = offendingValue;
    }

    public String getOffense() {
        return offense;
    }

    public void setOffense(String offense) {
        this.offense = offense;
    }

    @Override
    public String toString() {
        return "Error{" + "offendingValue=" + offendingValue +
                ", offense=" + offense + '}';
    }
}
