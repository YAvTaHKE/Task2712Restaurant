package main.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage { //AdvertisementStorage - хранилище рекламных роликов.
    //5. Так как хранилище рекламных роликов AdvertisementStorage единственное для всего ресторана, то сделаем его синглтоном.
    //Синглтон AdvertisementStorage должен иметь публичный статический метод getInstance

    private static AdvertisementStorage instance;
    private final List<Advertisement> videos = new ArrayList<>();

    private AdvertisementStorage() {
        Object someContent = new Object();
        videos.add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min
        videos.add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min
        videos.add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60)); //10 min
        videos.add(new Advertisement(someContent, "четвертое видео", 200, 5, 5 * 60)); //15 min
        videos.add(new Advertisement(someContent, "Пятое видео", 8000, 1, 60)); //10 min
    }

    private static AdvertisementStorage ourInstance = new AdvertisementStorage();

    public static AdvertisementStorage getInstance() {
        return ourInstance;
    }

    //Опишем его.
    //1. Видео должно где-то храниться, пусть это будет список.
    //Создадим поле videos и инициализируем его пустым листом.
    //Подумай, должно ли поле videos иметь возможность менять свое значение?


    //2. Чтобы как-то работать с видео, создай публичные методы:
    //2.1. list() - который вернет список всех существующих доступных видео.
    //2.2. add(Advertisement advertisement) - который добавит новое видео в список videos.

    //3. В конструкторе класса добавим в список videos какие-то данные. У меня это:
    //Object someContent = new Object();
    //new Advertisement(someContent, "First Video", 5000, 100, 3 * 60) // 3 min
    //new Advertisement(someContent, "Second Video", 100, 10, 15 * 60) //15 min
    //new Advertisement(someContent, "Third Video", 400, 2, 10 * 60) //10 min
    public List<Advertisement> list() { //который вернет список всех существующих доступных видео.
        return videos;
    }

    public void add(Advertisement advertisement) { //который добавит новое видео в список videos.
        videos.add(advertisement);
    }

}
