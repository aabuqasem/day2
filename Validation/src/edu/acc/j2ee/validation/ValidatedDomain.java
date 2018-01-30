package edu.acc.j2ee.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ValidatedDomain {
    private final Map<String, List<Error>> errors = new HashMap<>();

    public Map<String, List<Error>> getErrors() { return errors; }
    
    public boolean hasErrors() { return !errors.isEmpty(); }
    
    public List<Error> getErrorsFor(String field) {
        return errors.get(field);
    }
    
    public void addError(String field, String value, String offense) {    
        if (errors.get(field) == null)
            errors.put(field, new ArrayList<>());
        List<Error> errorList = errors.get(field);
        errorList.add(new Error(value, offense));
    }
    
    public void validate(boolean shouldThrow) {
        errors.clear();
        try { validate(); }
        catch (ValidationException ve) {
            if (shouldThrow) throw ve;
        }
    }
    
    protected abstract void validate() throws ValidationException;
}
