package main.ad;

import java.util.ArrayList;
import java.util.List;

//Класс синглтон  - хранилище рекламных роликов.
public class AdvertisementStorage {

    private static AdvertisementStorage instance = new AdvertisementStorage();
    private final List<Advertisement> videos = new ArrayList<>();

    private AdvertisementStorage() {

        //Инициализируем список видео какими-то данными
        Object someContent = new Object();
        videos.add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min
        videos.add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min
        videos.add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60)); //10 min
        videos.add(new Advertisement(someContent, "четвертое видео", 200, 5, 5 * 60)); //15 min
        videos.add(new Advertisement(someContent, "Пятое видео", 8000, 1, 60)); //10 min
    }

    public static AdvertisementStorage getInstance() {
        return instance;
    }

    //который возвращает список всех существующих доступных видео.
    public List<Advertisement> list() {
        return videos;
    }

    //который добавляет новое видео в список videos.
    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }
}
