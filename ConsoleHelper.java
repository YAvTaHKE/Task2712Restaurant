package main;

import main.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {

    //объект для считывания с клавиатуры
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    //вывести строку в консоль
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    //считать строку с клавиатуры
    public static String readString() {

        String result = "";
        try {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //просит пользователя выбрать блюдо и добавляет его в список.
    public static List<Dish> getAllDishesForOrder() {
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
    }
}