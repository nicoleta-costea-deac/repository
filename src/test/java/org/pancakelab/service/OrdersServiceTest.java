package org.pancakelab.service;

import org.junit.jupiter.api.*;
import org.pancakelab.exception.ValidationException;
import org.pancakelab.model.order.Order;
import org.pancakelab.model.pancake.PancakeOrder;
import org.pancakelab.model.pancake.PancakeType;
import org.pancakelab.repository.OrderRepository;
import org.pancakelab.validation.OrderValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdersServiceTest {

    private final OrderRepository pancakeRepository = new OrderRepository();
    private final OrderValidator orderValidator = new OrderValidator();
    private final OrdersService ordersService = new OrdersService(pancakeRepository, orderValidator);
    private UUID orderId = null;

    private final static String DARK_CHOCOLATE_PANCAKE = "DARK_CHOCOLATE";
    private final static String MILK_CHOCOLATE_PANCAKE = "MILK_CHOCOLATE";
    private final static String MILK_CHOCOLATE_HAZELNUTS_PANCAKE = "MILK_CHOCOLATE_HAZELNUTS";

    private final static String DARK_CHOCOLATE_PANCAKE_DESCRIPTION = "Delicious pancake with dark chocolate!";
    private final static String MILK_CHOCOLATE_PANCAKE_DESCRIPTION = "Delicious pancake with milk chocolate!";
    private final static String MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION = "Delicious pancake with milk chocolate, hazelnuts!";

    @Test
    @org.junit.jupiter.api.Order(10)
    public void GivenOrderDoesNotExist_WhenCreatingOrder_ThenOrderCreatedWithCorrectData_Test() throws ValidationException {
        // setup
        List<PancakeOrder> pancakeOrderList = new ArrayList<>();
        pancakeOrderList.add(PancakeOrder.of(PancakeType.DARK_CHOCOLATE, 2));
        pancakeOrderList.add(PancakeOrder.of(PancakeType.MILK_CHOCOLATE, 1));

        // exercise
        orderId = ordersService.createOrder(10, 20, pancakeOrderList);

        // verify
        List<String> orderDetails = ordersService.viewOrder(orderId);
        assertEquals(orderDetails, List.of("1 X Delicious pancake with milk chocolate!", "2 X Delicious pancake with dark chocolate!"));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    public void GivenOrderExists_WhenAddingPancakes_ThenCorrectNumberOfPancakesAdded_Test() throws ValidationException {
        // setup
        List<PancakeOrder> pancakeOrderList = new ArrayList<>();
        pancakeOrderList.add(PancakeOrder.of(PancakeType.DARK_CHOCOLATE, 2));
        pancakeOrderList.add(PancakeOrder.of(PancakeType.MILK_CHOCOLATE, 1));
        orderId = ordersService.createOrder(10, 20, pancakeOrderList);

        // exercise
        addPancakes();

        // verify
        List<String> ordersPancakes = ordersService.viewOrder(orderId);

        assertEquals(
                ordersPancakes,
                List.of(
                        "3 X " + MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                        "4 X " + MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                        "5 X " + DARK_CHOCOLATE_PANCAKE_DESCRIPTION));
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    public void GivenPancakesExists_WhenRemovingPancakes_ThenCorrectNumberOfPancakesRemoved_Test() throws ValidationException {
        // setup

        // exercise
        ordersService.removePancakes(PancakeOrder.of(PancakeType.DARK_CHOCOLATE, 2), orderId);
        ordersService.removePancakes(PancakeOrder.of(PancakeType.MILK_CHOCOLATE, 3), orderId);
        ordersService.removePancakes(PancakeOrder.of(PancakeType.MILK_CHOCOLATE_HAZELNUTS, 1), orderId);

        // verify
        List<String> ordersPancakes = ordersService.viewOrder(orderId);

        assertEquals(ordersPancakes, List.of(
                        "1 X " + MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                        "2 X " + MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                        "3 X " + DARK_CHOCOLATE_PANCAKE_DESCRIPTION));
    }

    @Test
    @org.junit.jupiter.api.Order(40)
    public void GivenOrderExists_WhenPreparingOrder_ThenOrderPrepared_Test() {
        // setup

        // exercise
        ordersService.prepareOrder(orderId);

        // verify
        Set<UUID> completedOrders = ordersService.listCompletedOrders();
        assertFalse(completedOrders.contains(orderId));

        Set<UUID> preparedOrders = ordersService.listPreparedOrders();
        assertTrue(preparedOrders.contains(orderId));
    }

    @Test
    @org.junit.jupiter.api.Order(50)
    public void GivenOrderExists_WhenCompletingOrder_ThenOrderCompleted_Test() {
        // setup

        // exercise
        ordersService.completeOrder(orderId);

        // verify
        Set<UUID> completedOrdersOrders = ordersService.listCompletedOrders();
        assertTrue(completedOrdersOrders.contains(orderId));
    }

    @Test
    @org.junit.jupiter.api.Order(60)
    public void GivenOrderExists_WhenDeliveringOrder_ThenCorrectOrderReturnedAndOrderRemovedFromTheDatabase_Test() {
        // setup
        List<String> pancakesToDeliver = ordersService.viewOrder(orderId);

        // exercise
        Object[] deliveredOrder = ordersService.deliverOrder(orderId);

        // verify
        Set<UUID> completedOrders = ordersService.listCompletedOrders();
        assertFalse(completedOrders.contains(orderId));

        Set<UUID> preparedOrders = ordersService.listPreparedOrders();
        assertFalse(preparedOrders.contains(orderId));

        List<String> ordersPancakes = ordersService.viewOrder(orderId);

        assertEquals(List.of(), ordersPancakes);
        assertEquals(orderId, ((Order) deliveredOrder[0]).getId());
        assertEquals(pancakesToDeliver, deliveredOrder[1]);
    }

    @Test
    @org.junit.jupiter.api.Order(70)
    public void GivenOrderExists_WhenCancellingOrder_ThenOrderAndPancakesRemoved_Test() throws ValidationException {
        // setup
        orderId = ordersService.createOrder(10, 20, List.of(PancakeOrder.of(PancakeType.MILK_CHOCOLATE, 3)));
        addPancakes();

        // exercise
        ordersService.cancelOrder(orderId);

        // verify
        Set<UUID> completedOrders = ordersService.listCompletedOrders();
        assertFalse(completedOrders.contains(orderId));

        Set<UUID> preparedOrders = ordersService.listPreparedOrders();
        assertFalse(preparedOrders.contains(orderId));

        List<String> ordersPancakes = ordersService.viewOrder(orderId);

        assertEquals(List.of(), ordersPancakes);
    }

    @Test
    @org.junit.jupiter.api.Order(80)
    public void GivenOrderIsNotFound_WhenAddingPancakesToOrder_ThenItReturnsFalse_Test() throws ValidationException {
        // set up
        var pancakeOrder = PancakeOrder.of(PancakeType.DARK_CHOCOLATE, 1);

        // exercise
        assertFalse(ordersService.addPancakes(pancakeOrder, UUID.randomUUID()));
    }

    @Test
    @org.junit.jupiter.api.Order(90)
    public void GivenOrderIsNotFound_WhenRemovingPancakesFromOrder_ThenItReturnsFalse_Test() throws ValidationException {
        // set up
        var pancakeOrder = PancakeOrder.of(PancakeType.DARK_CHOCOLATE, 1);

        // exercise
        assertFalse(ordersService.removePancakes(pancakeOrder, UUID.randomUUID()));
    }

    private void addPancakes() throws ValidationException {
        ordersService.addPancakes(PancakeOrder.of(PancakeType.DARK_CHOCOLATE , 3), orderId);
        ordersService.addPancakes(PancakeOrder.of(PancakeType.MILK_CHOCOLATE, 3), orderId);
        ordersService.addPancakes(PancakeOrder.of(PancakeType.MILK_CHOCOLATE_HAZELNUTS, 3), orderId);
    }
}
