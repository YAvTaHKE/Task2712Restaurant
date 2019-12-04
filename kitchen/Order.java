package main.kitchen;

import main.ConsoleHelper;
import main.Tablet;


import java.util.List;

public class Order {
    //В классе Order (заказ) должна быть информация, относящаяся к списку выбранных пользователем блюд.
    //Т.е. где-то должен быть список всех блюд, и должен быть список выбранных блюд в классе Order.
    //В классе Order нужны поля private final Tablet tablet и protected List<Dish> dishes.

    //Конструктор должен принимать один параметр типа Tablet и инициализировать поле tablet.
    private final Tablet tablet; // планшет
    protected List<Dish> dishes; //список выбранных блюд


    public Tablet getTablet() {
        return tablet;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Order(Tablet tablet) {
        this.tablet = tablet;
        initDishes();
    }

    public int getTotalCookingTime() { //метод public int getTotalCookingTime возвращающий количество времени требующееся на приготовление текущего заказа.
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
        //В классе Order создай вспомогательный метод public boolean isEmpty(),
        // который будет определять, есть ли какие либо блюда в заказе.
        return dishes.isEmpty() ? true : false;
    }

    protected void initDishes(){
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

}
//4. Перепиши метод toString в классе Order. Пусть он возвращает пустую строку, если нет блюд в заказе,
// иначе вывод должен быть аналогичным примеру в порядке добавления блюд. Используй ConsoleHelper.
//Также измени метод toString в классе Tablet (внутри класса Tablet нажмите Alt+Insert -> toString()).
//Пример:
//Your order: [Juice, Fish] of Tablet{number=5}


//3. Вернемся к классу Order: в нем есть ссылка на планшет, и еще есть список выбранных блюд.
//Инициализируй список dishes в конструкторе, вызвав метод getAllDishesForOrder из ConsoleHelper.
