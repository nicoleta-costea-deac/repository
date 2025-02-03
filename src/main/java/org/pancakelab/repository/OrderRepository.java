package org.pancakelab.repository;

import org.pancakelab.log.OrderLog;
import org.pancakelab.model.OrderItem;
import org.pancakelab.model.order.Order;
import org.pancakelab.model.order.OrderStatus;
import org.pancakelab.model.pancake.PancakeOrder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderRepository {

    public static final String LINE_FEED = "\n";

    private final Map<UUID, Order> activeOrders = new ConcurrentHashMap<>();
    private final Map<UUID, Order> inactiveOrders = new ConcurrentHashMap<>();

    public void createOrder(final Order order) {
        activeOrders.put(order.getId(), order);
    }

    public Order getActiveOrder(final UUID orderId) {
        return activeOrders.get(orderId);
    }

    public Order getInactiveOrder(final UUID orderId) {
        return inactiveOrders.get(orderId);
    }

    public List<String> viewOrder(final UUID orderId) {
        return Optional.ofNullable(activeOrders.get(orderId))
                .map(order -> order.getPancakes().values().stream()
                        .map(OrderItem::toString)
                        .sorted()
                        .toList())
                .orElse(Collections.emptyList());
    }

    public Set<UUID> listOrderIdsByStatus(final OrderStatus status) {
        return Stream.concat(activeOrders.values().stream(), inactiveOrders.values().stream())
                .filter(order -> order.getStatus() == status)
                .map(Order::getId)
                .collect(Collectors.toSet());
    }


    public void addPancakesToOrder(final UUID orderId, final PancakeOrder pancakeOrder) {
        Order order = activeOrders.get(orderId);

        if (order == null) {
            return;
        }

        order.addPancakes(pancakeOrder);

        Order activeOrder = getActiveOrder(orderId);
        String description = activeOrder.getPancakes().values().stream()
                .map(orderItem -> orderItem.getPancake().getPancakeType() + ": " + orderItem.getPancake().description())
                .collect(Collectors.joining(LINE_FEED));
        OrderLog.logAddPancake(activeOrder, description);
    }

    public void removePancakesFromOrder(final UUID orderId, final PancakeOrder pancakeOrder) {
        Order order = activeOrders.get(orderId);

        if (order == null) {
            return;
        }

        order.removePancakes(pancakeOrder);

        Order activeOrder = getActiveOrder(orderId);
        String description = activeOrder.getPancakes().values().stream()
                .map(orderItem ->  orderItem.getPancake().description())
                .collect(Collectors.joining(LINE_FEED));
        OrderLog.logRemovePancakes(activeOrder, description, pancakeOrder.getCount());
    }

    public void prepareOrder(final UUID orderId) {
        updateOrderStatus(orderId, OrderStatus.PREPARED);
    }

    public void completeOrder(final UUID orderId) {
        updateOrderStatus(orderId, OrderStatus.COMPLETED);
        OrderLog.logCompleteOrder(getActiveOrder(orderId));
    }

    public void cancelOrder(final UUID orderId) {
        updateOrderStatus(orderId, OrderStatus.CANCELLED);
        OrderLog.logCancelOrder(getInactiveOrder(orderId));
    }

    public void deliverOrder(final UUID orderId) {
        updateOrderStatus(orderId, OrderStatus.DELIVERED);
        OrderLog.logDeliverOrder(getInactiveOrder(orderId));
    }

    private void updateOrderStatus(final UUID orderId, final OrderStatus status) {
        Order order = activeOrders.get(orderId);

        if (order == null) {
            return;
        }

        order.updateStatus(status);

        if (status == OrderStatus.CANCELLED || status == OrderStatus.DELIVERED) {
            activeOrders.remove(orderId);
            inactiveOrders.put(orderId, order);
        }
    }
}
