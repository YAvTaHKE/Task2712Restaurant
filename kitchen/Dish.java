package main.kitchen;

//Список блюд и время готовки в минутах
public enum Dish {
    //Блюда
    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);

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

        StringBuilder fullList = new StringBuilder();


        for (Dish dish : values()) {
            fullList.append(dish).append(", ");
        }

        fullList.delete(fullList.length() - 2, fullList.length());

        return fullList.toString();
    }
}

