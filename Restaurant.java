package main;

import main.kitchen.Cook;
import main.kitchen.Order;
import main.kitchen.Waiter;
import main.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {

    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        //Создаем двух поваров в отдельных потоках
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


        //Создаем 5 планшетов
        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

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
            Thread.currentThread().sleep(100);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //15.3 создаем планшет директора и вызываем методы вывода статистики
        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printActiveVideoSet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printArchivedVideoSet();
        directorTablet.printCookWorkloading();


        //8. В методе main класса Restaurant должен быть создан новый повар и добавлен планшету
        // в качестве наблюдателя с помощью метода addObserver.

        // tablet1.addObserver(cook); // повар, наблюдатель за планшетом ******************

        //3. Пишем main.
        //Для объекта Observable добавляем свой объект Observer. См. п.2 и описание паттерна в wikipedia
        //Называем повара, имя не влияет на тесты. В моем варианте - это Amigo : )
        //
        //Сверим выводы в консоль. Пример моего вывода:
        //Your order: [Soup] of Tablet{number=5}
        //Start cooking - Your order: [Soup] of Tablet{number=5}
        //
        //4. Не забудь сразу после создания заказа и вывода информации о нем в консоль (найдите это место в коде) сделать следующее:
        //4.1. Установить флаг setChanged()
        //4.2. Отправить обсерверу заказ - notifyObservers(order);
        //
        //5. Также внесем небольшое изменение. Сделай так чтобы метод createOrder возвращал текущий заказ или null, если заказ создать не удалось.
        // *********
        //6. В методе main класса Restaurant должен быть создан новый официант и добавлен повару в качестве
        // наблюдателя с помощью метода addObserver.

    }
}
