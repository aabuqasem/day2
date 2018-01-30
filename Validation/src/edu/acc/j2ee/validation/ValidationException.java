package edu.acc.j2ee.validation;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private Map<String, List<Error>> errors;
    
    public ValidationException(Map<String, List<Error>> errors) {
        this.errors = errors;
    }
    
    public Map<String, List<Error>> getErrors() { return errors; }
}
