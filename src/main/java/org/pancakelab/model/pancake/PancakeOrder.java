package org.pancakelab.model.pancake;

import org.pancakelab.exception.ValidationException;
import org.pancakelab.validation.ValidatorService;
import org.pancakelab.validation.Validatable;

public class PancakeOrder implements Validatable {

    private final PancakeType pancakeType;
    private final int count;

    private PancakeOrder(PancakeType pancakeType, int count) {
        this.pancakeType = pancakeType;
        this.count = count;
    }

    public PancakeType getPancakeType() {
        return pancakeType;
    }

    public Integer getCount() {
        return count;
    }

    public static PancakeOrder of(PancakeType pancakeType, int count) {
        return new PancakeOrder(pancakeType, count);
    }

    @Override
    public void accept(ValidatorService visitor) throws ValidationException {
        visitor.validate(this);
    }
}
