package org.pancakelab.validation;

import org.pancakelab.exception.ValidationException;

public interface Validatable {

    void accept(ValidatorService visitor) throws ValidationException;
}
