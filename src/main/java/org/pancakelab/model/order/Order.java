package org.pancakelab.model.order;

import org.pancakelab.model.OrderItem;
import org.pancakelab.model.address.Address;
import org.pancakelab.model.pancake.Pancake;
import org.pancakelab.model.pancake.PancakeOrder;
import org.pancakelab.model.pancake.PancakeType;

import java.util.*;
import java.util.stream.Collectors;

public class Order {

    private final UUID id;
    private final Address address;
    private final Map<PancakeType, OrderItem> pancakes;
    private OrderStatus status;

    private Order(final Address address, final Map<PancakeType, OrderItem> pancakes) {
        this.id = UUID.randomUUID();
        this.address = address;
        this.pancakes = pancakes;
        this.status = OrderStatus.NEW;
    }

    public UUID getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public Map<PancakeType, OrderItem> getPancakes() {
        return Map.copyOf(pancakes);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addPancakes(final PancakeOrder pancakeOrder) {
        OrderItem orderedPancakes = pancakes.get(pancakeOrder.getPancakeType());
        if (orderedPancakes == null) {
            pancakes.put(pancakeOrder.getPancakeType(), OrderItem.of(Pancake.of(pancakeOrder.getPancakeType()), pancakeOrder.getCount()));
        } else {
            orderedPancakes.increaseCount(pancakeOrder.getCount());
        }
    }

    public void removePancakes(final PancakeOrder pancakeOrder) {
        OrderItem orderedPancakes = pancakes.get(pancakeOrder.getPancakeType());
        if (orderedPancakes == null) {
            return;
        }

        Integer count = orderedPancakes.getCount();

        if (count > pancakeOrder.getCount()) {
            orderedPancakes.decreaseCount(pancakeOrder.getCount());
        } else {
            pancakes.remove(pancakeOrder.getPancakeType());
        }
    }

    public void updateStatus(final OrderStatus status) {
        this.status = status;
    }

    public static Order of(final Address address, final List<PancakeOrder> pancakeOrderList) {
        Map<PancakeType, OrderItem> pancakes = pancakeOrderList.stream()
                .collect(Collectors.toMap(
                        PancakeOrder::getPancakeType,
                        order -> OrderItem.of(Pancake.of(order.getPancakeType()), order.getCount())
                ));

        return new Order(address, pancakes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
