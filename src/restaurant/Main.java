// src/restaurant/Main.java
package restaurant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numCustomers = 3;
        int numWaiters = 2;
        int numCooks = 2;
        long simulationDuration = 30;

        System.out.println("Запуск симуляции ресторана...");
        System.out.printf("Клиенты: %d, Официанты: %d, Повара: %d, Длительность: %ds%n",
                numCustomers, numWaiters, numCooks, simulationDuration);

        ExecutorService cookPool = Executors.newFixedThreadPool(numCooks);
        ExecutorService servicePool = Executors.newCachedThreadPool();

        Kitchen kitchen = new Kitchen(cookPool);

        for (int i = 0; i < numWaiters; i++) {
            servicePool.submit(new Waiter(i, kitchen));
        }

        for (int i = 0; i < numCustomers; i++) {
            servicePool.submit(new Customer(i, kitchen));
        }

        Thread.sleep(TimeUnit.SECONDS.toMillis(simulationDuration));

        servicePool.shutdownNow();
        cookPool.shutdownNow();

        System.out.println("\nСимуляция завершена.");
    }
}