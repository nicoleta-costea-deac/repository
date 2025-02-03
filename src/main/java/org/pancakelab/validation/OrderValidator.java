package org.pancakelab.validation;

import org.pancakelab.exception.ValidationException;
import org.pancakelab.model.address.Address;
import org.pancakelab.model.pancake.PancakeOrder;

import java.util.ArrayList;
import java.util.List;

public class OrderValidator implements ValidatorService {

    public static final int MIN_VALID_BUILDING = 1;
    public static final int MAX_VALID_BUILDING = 1000;

    public static final int MIN_VALID_ROOM = 1;
    public static final int MAX_VALID_ROOM = 10000;
    public static final int MIN_NUMBER_OF_PANCAKES = 1;
    public static final int MAX_NUMBER_OF_PANCAKES = 100;

    public void validateInput(final Address address, final List<PancakeOrder> pancakeOrderList) throws ValidationException {
        List<Validatable> elementsToBeValidated = new ArrayList<>();
        elementsToBeValidated.add(address);
        elementsToBeValidated.addAll(pancakeOrderList);

        for (Validatable element : elementsToBeValidated) {
            element.accept(this);
        }
    }

    public void validateInput(PancakeOrder pancakeOrder) throws ValidationException {
        pancakeOrder.accept(this);
    }

    @Override
    public void validate(Address address) throws ValidationException {
        if (address.getBuilding() < MIN_VALID_BUILDING || address.getBuilding() > MAX_VALID_BUILDING) {
            throw new ValidationException("Building does not exist!");
        }

        if (address.getRoom() < MIN_VALID_ROOM|| address.getRoom() > MAX_VALID_ROOM) {
            throw new ValidationException("Room does not exist!");
        }
    }

    @Override
    public void validate(PancakeOrder pancakeOrder) throws ValidationException {
        if (pancakeOrder.getCount() < MIN_NUMBER_OF_PANCAKES || pancakeOrder.getCount() > MAX_NUMBER_OF_PANCAKES) {
            throw new ValidationException("Please order a maximum of 100 pancakes at once!");
        }
    }
}
