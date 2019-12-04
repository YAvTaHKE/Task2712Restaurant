package main;

import main.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    public static void writeMessage(String message) {
        System.out.println(message);
    }//для вывода message в консоль
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public static String readString() {

        String result = "";
        try {
            result = reader.readLine();
        } catch (IOException e) {
        }
        return result;
    } //для чтения строки с консоли

    public static List<Dish> getAllDishesForOrder() { // тут делается заказ!!!
        //Выведи список всех блюд и попроси пользователя ввести строку - название блюда.
        //Введенное 'exit' означает завершение заказа.
        //В случае, если введенное блюдо не представлено в меню, выведи сообщение о том, что такого блюда нет
        // и продолжи формирование заказа.
        //Исключения ввода-вывода бросай выше, на этом уровне не понятно, что с ними делать.
        ConsoleHelper.writeMessage(Dish.allDishesToString());
        writeMessage("Блюдо или введите exit:");
        List<Dish> dishesOrder = new ArrayList<>();
        while (true) {
            String dish = ConsoleHelper.readString();
            if (dish.equalsIgnoreCase("exit")) {
                break;
            } else {
                try {
                    dishesOrder.add(Dish.valueOf(dish));
                } catch (Exception e) {
                    ConsoleHelper.writeMessage(dish + " -- такого блюда нет");
                }
            }
        }
        return dishesOrder;

    } //просит пользователя выбрать блюдо и добавляет его в список.
}

//Требования:
//1. Метод writeMessage класса ConsoleHelper должен выводить полученную строку в консоль.
//2. Метод readString класса ConsoleHelper должен возвращать строку считанную с консоли.
//3. Метод getAllDishesForOrder класса ConsoleHelper должен возвращать список блюд выбранных пользователем.
//4. В конструкторе класса Order список dishes
// должен быть инициализирован результатом работы метода getAllDishesForOrder.