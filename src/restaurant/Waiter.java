package restaurant;

public class Waiter implements Runnable {
    private final int id;
    private final Kitchen kitchen;

    public Waiter(int id, Kitchen kitchen) {
        this.id = id;
        this.kitchen = kitchen;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Order readyOrder = kitchen.takeReadyOrder();
                System.out.printf("Официант-%d доставляет заказ %s клиенту %s%n",
                        id, readyOrder.getDishType(), readyOrder.getCustomerName());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}