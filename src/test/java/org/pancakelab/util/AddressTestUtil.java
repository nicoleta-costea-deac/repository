package org.pancakelab.util;

import org.pancakelab.model.address.Address;

public class AddressTestUtil {

    public static Address createAddress(int building, int room) {
        return Address.of(building, room);
    }
}
