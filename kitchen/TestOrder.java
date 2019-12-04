package main.kitchen;

import main.Tablet;

import java.util.ArrayList;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) {
        super(tablet);
    }

    @Override
    protected void initDishes() {
        dishes = new ArrayList<>();
        //Список меню
        Dish[] allDishes = Dish.values();

        int random = (int) (Math.random()*10);

        for (int i = 0; i < random; i++) {
            int dishRandom = (int) (Math.random() * allDishes.length);
            dishes.add(allDishes[dishRandom]);
        }

    }
}
