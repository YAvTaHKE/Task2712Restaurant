package main;

import java.util.List;

/*
Класс для случайного распределения заказов между планшетами
 */

public class RandomOrderGeneratorTask implements Runnable {

    //Список планшетов
    private List<Tablet> tablets;

    //Интервал между созданиями рандомных заказов
    private int interval;

    RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //Выбираем случайный планшет
            Tablet randomTablet = tablets.get((int)(Math.random()*tablets.size()));
            //Создаем случайный заказ
            randomTablet.createTestOrder();

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
