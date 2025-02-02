package org.pancakelab.model;

import org.pancakelab.model.pancake.Pancake;

import java.util.Objects;

public class OrderItem {

    private Pancake pancake;
    private Integer count;

    private OrderItem(Pancake pancake, Integer count) {
        this.pancake = pancake;
        this.count = count;
    }

    public Pancake getPancake() {
        return pancake;
    }

    public Integer getCount() {
        return count;
    }

    public void setPancake(Pancake pancake) {
        this.pancake = pancake;
    }

    public void increaseCount(Integer count) {
        this.count += count;
    }

    public void decreaseCount(Integer count) {
        this.count -= count;
    }

    public static OrderItem of(Pancake pancake, Integer count) {
        return new OrderItem(pancake, count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(pancake, orderItem.pancake) && Objects.equals(count, orderItem.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pancake, count);
    }

    @Override
    public String toString() {
        return this.count + " X " + this.pancake.description();
    }
}
