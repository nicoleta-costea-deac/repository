package org.pancakelab.service;

import org.pancakelab.exception.ValidationException;
import org.pancakelab.log.OrderLog;
import org.pancakelab.model.address.Address;
import org.pancakelab.model.order.Order;
import org.pancakelab.model.order.OrderStatus;
import org.pancakelab.model.pancake.PancakeOrder;
import org.pancakelab.repository.OrderRepository;
import org.pancakelab.validation.OrderValidator;

import java.util.*;

public class OrdersService {

    private final OrderRepository orderRepository;
    private final OrderValidator validatorService;

    public OrdersService(final OrderRepository orderRepository, final OrderValidator validatorService) {
        this.orderRepository = orderRepository;
        this.validatorService = validatorService;

    }

    public UUID createOrder(final int buildingNumber, final int room, final List<PancakeOrder> pancakeOrderList) throws ValidationException {
        try {
            // Build order
            final Address address = Address.of(buildingNumber, room);

            // Validate input parameters
            validatorService.validateInput(address, pancakeOrderList);

            // Build Pancakes Order
            final Order newOrder = Order.of(address, pancakeOrderList);

            // Create order
            orderRepository.createOrder(newOrder);

            return newOrder.getId();
        } catch (final ValidationException exception) {
            OrderLog.logValidationError(exception.getMessage());
            throw exception;
        }
    }

    public boolean addPancakes(final PancakeOrder pancakeOrder, final UUID orderId) throws ValidationException {
        // Check if oder exists and return without changing the repository, in case it doesn't
        if (orderRepository.getActiveOrder(orderId) == null) {
            return false;
        }

        try {
            // Validate input parameters
            validatorService.validateInput(pancakeOrder);

            // Update Order
            orderRepository.addPancakesToOrder(orderId, pancakeOrder);
        } catch (final ValidationException exception) {
            OrderLog.logValidationError(exception.getMessage());
            throw exception;
        }

        return true;
    }

    public boolean removePancakes(final PancakeOrder pancakeOrder, final UUID orderId) throws ValidationException {
        // Check if oder exists and return without changing the repository, in case it doesn't
        if (orderRepository.getActiveOrder(orderId) == null) {
            return false;
        }

        try {
            // Validate input parameters
            validatorService.validateInput(pancakeOrder);

            // Remove pancakes
            orderRepository.removePancakesFromOrder(orderId, pancakeOrder);
        } catch (final ValidationException exception) {
            OrderLog.logValidationError(exception.getMessage());
            throw exception;
        }

        return true;
    }

    public Set<UUID> listCompletedOrders() {
        return orderRepository.listOrderIdsByStatus(OrderStatus.COMPLETED);
    }

    public Set<UUID> listPreparedOrders() {
        return orderRepository.listOrderIdsByStatus(OrderStatus.PREPARED);
    }

    public List<String> viewOrder(final UUID orderId) {
        return orderRepository.viewOrder(orderId);
    }

    public Object[] deliverOrder(UUID orderId) {
        final List<String> pancakesToDeliver = viewOrder(orderId);
        final Order orderToDeliver = orderRepository.getActiveOrder(orderId);

        orderRepository.deliverOrder(orderId);

        return new Object[] {orderToDeliver, pancakesToDeliver};
    }

    public void cancelOrder(final UUID orderId) {
        orderRepository.cancelOrder(orderId);
    }

    public void completeOrder(final UUID orderId) {
        orderRepository.completeOrder(orderId);
    }

    public void prepareOrder(final UUID orderId) {
        orderRepository.prepareOrder(orderId);
    }

}
