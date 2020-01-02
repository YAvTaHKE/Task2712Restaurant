package main;

import main.kitchen.Cook;
import main.kitchen.Order;
import main.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    //Интервал для автоматической генерации заказов
    private static final int ORDER_CREATING_INTERVAL = 100;
    //Очередь для заказов
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        //Создаем двух поваров с поддержкой Runnable и передаем им ссылку на очередь заказов
        Cook firstCook = new Cook("Amigo");
        firstCook.setQueue(orderQueue);

        Cook secondCook = new Cook("Coca");
        secondCook.setQueue(orderQueue);


        //Применяем паттерн Observers для оповещения
        //Создаем официанта (waiter) слушателя
        Waiter waiter = new Waiter();
        //Добавляем официанта в качестве слушателя у поваров
        firstCook.addObserver(waiter);
        secondCook.addObserver(waiter);


        //Создаем 5 планшетов и передаем им ссылку на очередь заказов
        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        //Запускаем поваров в отдельных потоках-демонах
        Thread firstCookThread = new Thread(firstCook);
        firstCookThread.setDaemon(true);
        firstCookThread.start();

        Thread secondCookThread = new Thread(secondCook);
        secondCookThread.setDaemon(true);
        secondCookThread.start();


        //Создаем нить, которая рандомно генерирует заказы для планшетов в течение 1 секунды
        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        thread.start();
        try {
            Thread.currentThread().sleep(1000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //создаем планшет директора
        DirectorTablet directorTablet = new DirectorTablet();
        //вызываем методы отображения статистики
        directorTablet.printActiveVideoSet(); //список активных роликов и оставшееся количество показов по каждому;
        directorTablet.printAdvertisementProfit();//какую сумму заработали на рекламе, сгруппировать по дням;
        directorTablet.printArchivedVideoSet(); //список неактивных роликов (с оставшемся количеством показов равным нулю).
        directorTablet.printCookWorkloading(); //загрузка (рабочее время) повара, сгруппировать по дням;
    }
}
