package org.pancakelab.log;

import org.pancakelab.model.order.Order;

public class OrderLog {

    private static final StringBuffer log = new StringBuffer();

    public static void logAddPancake(Order order, String description) {
        long pancakesInOrder = order.getPancakes().size();

        log.append("Added pancake with description '%s' ".formatted(description))
           .append("to order %s containing %d pancakes, ".formatted(order.getId(), pancakesInOrder))
           .append("for building %d, room %d.".formatted(order.getAddress().getBuilding(), order.getAddress().getRoom()))
           .append("\n");
    }

    public static void logRemovePancakes(final Order order, String description, int count) {
        long pancakesInOrder = order.getPancakes().size();

        log.append("Removed %d pancake(s) with description '%s' ".formatted(count, description))
           .append("from order %s now containing %d pancakes, ".formatted(order.getId(), pancakesInOrder))
           .append("for building %d, room %d.".formatted(order.getAddress().getBuilding(), order.getAddress().getRoom()))
           .append("\n");
    }

    public static void logCancelOrder(final Order order) {
        long pancakesInOrder = order.getPancakes().size();
        log.append("Cancelled order %s with %d pancakes ".formatted(order.getId(), pancakesInOrder))
           .append("for building %d, room %d.".formatted(order.getAddress().getBuilding(), order.getAddress().getRoom()))
           .append("\n");
    }

    public static void logCompleteOrder(final Order order) {
        long pancakesInOrder = order.getPancakes().size();
        log.append("Completed order %s with %d pancakes ".formatted(order.getId(), pancakesInOrder))
           .append("for building %d, room %d.".formatted(order.getAddress().getBuilding(), order.getAddress().getRoom()))
           .append("\n");;
    }

    public static void logDeliverOrder(final Order order) {
        long pancakesInOrder = order.getPancakes().size();
        log.append("Order %s with %d pancakes ".formatted(order.getId(), pancakesInOrder))
           .append("for building %d, room %d out for delivery.".formatted(order.getAddress().getBuilding(), order.getAddress().getRoom()))
           .append("\n");;
    }

    public static void logValidationError(String message) {
        log.append("Validation error: ").append(message).append("\n");
    }

    public static String getLog() {
        return log.toString();
    }
}
