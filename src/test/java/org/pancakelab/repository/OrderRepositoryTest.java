package org.pancakelab.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pancakelab.model.OrderItem;
import org.pancakelab.model.order.Order;
import org.pancakelab.model.order.OrderStatus;
import org.pancakelab.model.pancake.PancakeOrder;
import org.pancakelab.model.pancake.PancakeType;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.pancakelab.util.OrderTestUtil.createOrder;
import static org.pancakelab.util.OrderTestUtil.createPancakeOrder;

public class OrderRepositoryTest {

    private OrderRepository orderRepository;
    private Order order;

    @BeforeEach
    public void setUp() {
        orderRepository = new OrderRepository();
        order = createOrder();
    }

    @AfterEach
    public void tearDown() {
        order = null;
        orderRepository = null;
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    public void testThat_OrderExistsInRepository_WhenOrderIsCreated() {
        // set up

        // exercise
        orderRepository.createOrder(order);

        // verify that a NEW order exists in the repository
        Set<UUID> orderIds = orderRepository.listOrderIdsByStatus(OrderStatus.NEW);
        assertEquals(orderIds.size(), 1);

        // verify that the order Id from the repository matches the order Id from test
        UUID orderId = orderIds.iterator().next();
        assertEquals(orderId, order.getId());

        // Check that the number of pancakes from the created order is
        // the expected number of pancakes
        Order createdOrder = orderRepository.getActiveOrder(orderId);
        assertEquals(
                1,
                createdOrder
                        .getPancakes()
                        .values()
                        .stream()
                        .mapToInt(OrderItem::getCount)
                        .sum());
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    public void testThat_OrderHasCorrectNumberOfPancake_WhenPancakesAreAdded() {
        // set up
        orderRepository.createOrder(order);
        PancakeOrder pancakeOrder = createPancakeOrder(PancakeType.MILK_CHOCOLATE, 3);

        // exercise
        UUID orderId = order.getId();
        orderRepository.addPancakesToOrder(orderId, pancakeOrder);

        // verify
        Order order = orderRepository.getActiveOrder(orderId);
        assertEquals(
                4,
                order
                        .getPancakes()
                        .values()
                        .stream()
                        .mapToInt(OrderItem::getCount)
                        .sum());
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    public void testThat_OrderHasCorrectNumberOfPancake_WhenPancakesAreRemoved() {
        // set up
        UUID orderId = order.getId();
        orderRepository.createOrder(order);
        PancakeOrder pancakeOrderToAdd = createPancakeOrder(PancakeType.MILK_CHOCOLATE, 3);
        orderRepository.addPancakesToOrder(orderId, pancakeOrderToAdd);
        PancakeOrder pancakeOrderToRemove = createPancakeOrder(PancakeType.MILK_CHOCOLATE, 2);

        // exercise
        orderRepository.removePancakesFromOrder(orderId, pancakeOrderToRemove);

        // verify
        Order order = orderRepository.getActiveOrder(orderId);
        assertEquals(
                2,
                order
                        .getPancakes()
                        .values()
                        .stream()
                        .mapToInt(OrderItem::getCount)
                        .sum());
    }
}
