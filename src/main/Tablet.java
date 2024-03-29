package main;

import main.ad.AdvertisementManager;
import main.ad.NoVideoAvailableException;
import main.kitchen.Order;
import main.kitchen.TestOrder;
import main.statistic.StatisticManager;
import main.statistic.event.NoAvailableVideoEventDataRow;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Tablet {
    //номер планшета
    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    //Общая чередь заказов
    private LinkedBlockingQueue<Order> queue;

    Tablet(int number) {
        this.number = number;
    }

    void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    //будет создавать заказ из тех блюд, которые выберет пользователь.
    public void createOrder() {
        Order order = null;
        try {
            order = new Order(this);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } finally {
            refact(order);
        }
    }

    //метод создает случайный заказ
    void createTestOrder(){
        TestOrder order = null;
        try {
            order = new TestOrder(this);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } finally {
            refact(order);
        }
    }

    //рефакторинг повторяющегося кода
    private void refact(Order order) {
        if (!order.isEmpty()) {
            try {
                queue.put(order);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
            try {
                advertisementManager.processVideos();
            } catch (NoVideoAvailableException e) {
                StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(order.getTotalCookingTime() * 60));
                logger.log(Level.INFO, "No video is available for the order " + order.toString());
            }
        }
    }
///ff
    @Override
    public String toString () {
        return "Tablet{number=" + number + "}";
    }
}
