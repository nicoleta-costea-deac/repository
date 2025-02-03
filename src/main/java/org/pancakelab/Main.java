package org.pancakelab;

import org.pancakelab.log.OrderLog;
import org.pancakelab.model.pancake.PancakeOrder;
import org.pancakelab.model.pancake.PancakeType;
import org.pancakelab.repository.OrderRepository;
import org.pancakelab.service.OrdersService;
import org.pancakelab.validation.OrderValidator;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        OrderSimulation orderSimulation1 = new OrderSimulation();
        OrderSimulation orderSimulation2 = new OrderSimulation();
        OrderSimulation orderSimulation3 = new OrderSimulation();
        OrderSimulation orderSimulation4 = new OrderSimulation();
        OrderSimulation orderSimulation5 = new OrderSimulation();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Future<String> simulationResult1 = executorService.submit(orderSimulation1);
        Future<String> simulationResult2 = executorService.submit(orderSimulation2);
        Future<String> simulationResult3 = executorService.submit(orderSimulation3);
        Future<String> simulationResult4 = executorService.submit(orderSimulation4);
        Future<String> simulationResult5 = executorService.submit(orderSimulation5);

        System.out.print(simulationResult1.get());
        System.out.print(simulationResult2.get());
        System.out.print(simulationResult3.get());
        System.out.print(simulationResult4.get());
        System.out.print(simulationResult5.get());

        Thread.sleep(2000);
        executorService.shutdown();
    }
}

class OrderSimulation implements Callable<String> {

    private final List<PancakeType> pancakeTypes = List.of(
            PancakeType.DARK_CHOCOLATE,
            PancakeType.DARK_CHOCOLATE_WHIPPED_CREAM_HAZELNUTS,
            PancakeType.DARK_CHOCOLATE_WHIPPED_CREAM,
            PancakeType.MILK_CHOCOLATE,
            PancakeType.MILK_CHOCOLATE_HAZELNUTS
    );

    private final OrderRepository orderRepository = new OrderRepository();
    private final OrderValidator orderValidator = new OrderValidator();
    private final OrdersService ordersService = new OrdersService(orderRepository, orderValidator);

    private final Random random = new Random();

    @Override
    public String call() {
        String threadName = Thread.currentThread().getName();
        try {
            UUID orderId = ordersService.createOrder(random.nextInt(1000), random.nextInt(10000), List.of());
            System.out.println(threadName + " created an order with id " + orderId + " and order items: " + ordersService.viewOrder(orderId));

            ordersService.addPancakes(PancakeOrder.of(pancakeTypes.get(random.nextInt(pancakeTypes.size())), random.nextInt(10)), orderId);
            ordersService.completeOrder(orderId);
            ordersService.prepareOrder(orderId);

            if (random.nextBoolean()) {
                ordersService.deliverOrder(orderId);
            } else {
                ordersService.cancelOrder(orderId);
            }

            ordersService.viewOrder(orderId);
            System.out.println(threadName + ": " + OrderLog.getLog());
            return threadName + " done!\n";
        } catch (Exception exception) {
            System.out.println(threadName + ": " + exception.getMessage());
            return threadName + " " + exception.getMessage() + "\n";
        }
    }
}