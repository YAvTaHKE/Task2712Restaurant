package main.kitchen;

import main.ConsoleHelper;
import main.Tablet;


import java.util.List;

public class Order {
    //В классе Order (заказ) должна быть информация, относящаяся к списку выбранных пользователем блюд.
    //Т.е. где-то должен быть список всех блюд, и должен быть список выбранных блюд в классе Order.

    private final Tablet tablet; // планшет
    protected List<Dish> dishes; //список выбранных блюд

    public Order(Tablet tablet) {
        this.tablet = tablet;
        initDishes();
    }

    public Tablet getTablet() {
        return tablet;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    //метод public int getTotalCookingTime возвращающий количество времени требующееся на приготовление текущего заказа.
    public int getTotalCookingTime() {
        int time = 0;
        for (Dish dish : dishes
        ) {
            time += dish.getDuration();
        }
        return time;
    }

    @Override
    public String toString() {
        return dishes.isEmpty() ? "" : "Your order: " + dishes + " of " + tablet + ", cooking time " + getTotalCookingTime() + "min";
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    protected void initDishes(){
        dishes = ConsoleHelper.getAllDishesForOrder();
    }
}
