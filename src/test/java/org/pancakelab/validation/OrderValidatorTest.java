package org.pancakelab.validation;

import org.junit.jupiter.api.Test;
import org.pancakelab.exception.ValidationException;
import org.pancakelab.model.address.Address;
import org.pancakelab.model.pancake.PancakeOrder;
import org.pancakelab.model.pancake.PancakeType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderValidatorTest {

    private final OrderValidator validator = new OrderValidator();

    @Test
    public void GivenThatAddressIsValid_WhenValidating_ThenNoExceptionIsThrown() {
        // set up
        var address = Address.of(10, 10000);

        // exercise
        assertDoesNotThrow(() -> validator.validate(address));
    }

    @Test
    public void GivenThatBuildingNumberIsNegative_WhenValidating_ThenExceptionIsThrown() {
        // set up
        var address = Address.of(-1, 10000);

        // exercise
        assertThrows(ValidationException.class, () -> validator.validate(address));
    }

    @Test
    public void GivenThatBuildingNumberIsTooHigh_WhenValidating_ThenExceptionIsThrown() {
        // set up
        var address = Address.of(2000, 10000);

        // exercise
        assertThrows(ValidationException.class, () -> validator.validate(address));
    }

    @Test
    public void GiveThatRoomNumberIsNegative_WhenValidating_ThenExceptionIsThrown() {
        // set up
        var address = Address.of(1, -10000);

        // exercise
        assertThrows(ValidationException.class, () -> validator.validate(address));
    }

    @Test
    public void GivenThatRoomNumberIsTooHigh_WhenValidating_ThenExceptionIsThrown() {
        // set up
        var address = Address.of(1, 10001);

        // exercise
        assertThrows(ValidationException.class, () -> validator.validate(address));
    }

    @Test
    public void GivenThatPancakeCountIsTooHigh_WhenValidating_ThenExceptionIsThrown() {
        // set up
        var pancakeOrder = PancakeOrder.of(PancakeType.MILK_CHOCOLATE, 101);

        // exercise
        assertThrows(ValidationException.class, () -> validator.validate(pancakeOrder));
    }

    @Test
    public void GivenThatPancakeCountIsZero_WhenValidating_ThenExceptionIsThrown() {
        // set up
        var pancakeOrder = PancakeOrder.of(PancakeType.MILK_CHOCOLATE, 0);

        // exercise
        assertThrows(ValidationException.class, () -> validator.validate(pancakeOrder));
    }
}
