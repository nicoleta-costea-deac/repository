package org.pancakelab.util;

import org.pancakelab.model.order.Order;
import org.pancakelab.model.pancake.PancakeOrder;
import org.pancakelab.model.pancake.PancakeType;

import java.util.List;

import static org.pancakelab.util.AddressTestUtil.createAddress;

public class OrderTestUtil {

    public static Order createOrder() {
        return Order.of(createAddress(1, 1), List.of(createPancakeOrder()));
    }

    public static PancakeOrder createPancakeOrder() {
        return PancakeOrder.of(PancakeType.DARK_CHOCOLATE, 1);
    }

    public static PancakeOrder createPancakeOrder(PancakeType type, int count) {
        return PancakeOrder.of(type, count);
    }
}
