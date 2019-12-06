package main.ad;


import java.util.ArrayList;
import java.util.List;

/* Класс синглтон будет предоставлять информацию из AdvertisementStorage в нужном нам виде
 */
public class StatisticAdvertisementManager {

    private static StatisticAdvertisementManager instance;
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager (){}

    public static StatisticAdvertisementManager getInstance(){
        if (instance == null) instance = new StatisticAdvertisementManager();
        return instance;
    }

    /* метод достает список активных и неактивных рекламных роликов.
Активным роликом считается тот, у которого есть минимум один доступный показ.
Неактивным роликом считается тот, у которого количество показов равно 0.
*/

    public List<Advertisement> getVideoSet(boolean isActive) {
        List<Advertisement> videoSet = new ArrayList<>();
        for (Advertisement ad : storage.list())
        {
            if (!isActive && ad.getHits() == 0)
            {
                videoSet.add(ad);
            }
            if (isActive && ad.getHits() != 0)
            {
                videoSet.add(ad);
            }
        }

        //сортировать по имени
        videoSet.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

        return videoSet;
    }
}
