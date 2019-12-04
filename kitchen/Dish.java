package main.kitchen;

import java.util.Arrays;

public enum Dish {
    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);
    //1.1. Измени создание элементов enum - Fish(25), Steak(30), Soup(15), Juice(5), Water(3);
    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    //6. Чтобы пользователь мог выбрать себе блюда, нужно их все ему отобразить.
    // Для этого в Dish создай метод public static String allDishesToString(),
    // который сформирует строку из всех блюд.
    public static String allDishesToString() {
        String fullList = "";
        for (Dish dish : values()
        ) {
            fullList += dish.toString() + ", ";
        }
        fullList = fullList.substring(0, fullList.length() - 2);
        return fullList;
    }

}
//5. Нам нужен класс Dish(Блюдо), создадим его в пакете kitchen.
// Пусть это будет enum со списком блюд: Fish, Steak, Soup, Juice, Water.
