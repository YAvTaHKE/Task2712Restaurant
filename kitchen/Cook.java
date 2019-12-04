package main.kitchen;

import main.ConsoleHelper;
import main.statistic.StatisticManager;
import main.statistic.event.CookedOrderEventDataRow;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
public class Cook extends Observable implements Runnable{
    private String name;
    //маркер занятости повора
    private boolean busy = false;
    //Очередь с заказами
    private LinkedBlockingQueue<Order> queue;

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Cook(String name) {
        this.name = name;
    }

    public boolean isBusy() {
        return busy;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {

        busy = true;


        ConsoleHelper.writeMessage("Start cooking - " + order + ", cooking time " + order.getTotalCookingTime() + "min");

        StatisticManager.getInstance().register(
                new CookedOrderEventDataRow(
                                            order.getTablet().toString(),
                                            this.name,
                            order.getTotalCookingTime()*60,
                                            order.getDishes()
                )
        );
        try {
            Thread.sleep(order.getTotalCookingTime() * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(this);

        busy = false;
    }



    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                    Order order = queue.take();
                    if (order != null)
                        startCookingOrder(order);
                    else break;


                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

