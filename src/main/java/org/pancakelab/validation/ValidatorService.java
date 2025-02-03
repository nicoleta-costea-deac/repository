package org.pancakelab.validation;

import org.pancakelab.exception.ValidationException;
import org.pancakelab.model.address.Address;
import org.pancakelab.model.pancake.PancakeOrder;

public interface ValidatorService {

    void validate(Address address) throws ValidationException;
    void validate(PancakeOrder sheep) throws ValidationException;
}
