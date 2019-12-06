package main.kitchen;

import main.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;

//1. Создадим класс Waiter(Официант) в пакете kitchen, он будет относить заказы назад к столику.
// Официант будет безымянным.

public class Waiter implements Observer {

    //3. Метод void update будет выводить в консоль сообщение о том, какой заказ и кем был приготовлен:
    //order + " was cooked by " + cook
    @Override
    public void update(Observable observable, Object arg) {
        ConsoleHelper.writeMessage(arg + " was cooked by " + observable);


    }
}