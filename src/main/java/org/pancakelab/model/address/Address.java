package org.pancakelab.model.address;

import org.pancakelab.exception.ValidationException;
import org.pancakelab.validation.ValidatorService;
import org.pancakelab.validation.Validatable;

public class Address implements Validatable {

    private final int building;
    private final int room;

    private Address(int building, int room) {
        this.building = building;
        this.room = room;
    }

    public static Address of(int building, int room) {
        return new Address(building, room);
    }

    public int getBuilding() {
        return building;
    }

    public int getRoom() {
        return room;
    }

    @Override
    public void accept(final ValidatorService visitor) throws ValidationException {
        visitor.validate(this);
    }
}
