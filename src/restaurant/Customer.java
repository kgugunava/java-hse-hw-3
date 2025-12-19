package restaurant;

import java.util.Random;

public class Customer implements Runnable {
    private final int id;
    private final Kitchen kitchen;
    private final Random random = new Random();

    public Customer(int id, Kitchen kitchen) {
        this.id = id;
        this.kitchen = kitchen;
    }

    @Override
    public void run() {
        String[] dishes = {"Паста", "Стейк", "Салат"};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String dish = dishes[random.nextInt(dishes.length)];
                Order order = new Order("Клиент-" + id, dish);
                System.out.printf("%s сделал заказ: %s%n", order.getCustomerName(), dish);
                kitchen.addOrder(order);

                Thread.sleep(random.nextInt(8000) + 3000); // 3-10 сек между заказами
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}