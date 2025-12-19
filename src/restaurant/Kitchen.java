package restaurant;

import java.util.concurrent.*;

public class Kitchen {
    private final BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Order> readyQueue = new LinkedBlockingQueue<>();
    private final ExecutorService cookPool;

    public Kitchen(ExecutorService cookPool) {
        this.cookPool = cookPool;
        startProcessingOrders();
    }

    public void addOrder(Order order) {
        try {
            orderQueue.put(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Order takeReadyOrder() throws InterruptedException {
        return readyQueue.take();
    }

    private void startProcessingOrders() {
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Order order = orderQueue.take();
                    cookPool.submit(() -> {
                        System.out.printf("Повар начинает готовить: %s%n", order);
                        int cookTime = switch (order.getDishType()) {
                            case "Стейк" -> 5000;
                            case "Паста" -> 6000;
                            default -> 4000;
                        };
                        try {
                            Thread.sleep(cookTime);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.printf("Повар закончил готовить: %s%n", order);
                        readyQueue.offer(order);
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}