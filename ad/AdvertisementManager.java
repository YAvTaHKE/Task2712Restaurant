package main.ad;

import main.ConsoleHelper;
import main.statistic.StatisticManager;
import main.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvertisementManager {
    //4. В AdvertisementManager создадим final поле-ссылку на экземпляр AdvertisementStorage и назовем ее storage.
//    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
//    //3. Определим необходимые данные для объекта AdvertisementManager - это время выполнения заказа поваром.
//    //Т.к. продолжительность видео у нас хранится в секундах, то и и время выполнения заказа тоже будем принимать в секундах.
//    //В классе AdvertisementManager создай конструктор, который принимает один параметр - int timeSeconds.
//    //Создай соответствующее поле и сохраните это значение в него.
//    int timeSeconds;
//
//    public AdvertisementManager(int timeSeconds) {
//        this.timeSeconds = timeSeconds;
//    }

    //4. AdvertisementManager выполняет только одно единственное действие - обрабатывает рекламное видео.
    //Поэтому создайте единственный публичный метод void processVideos(), его функционал опишем в следующем задании.
    //А пока выведем в консоль "calling processVideos method"

//    public void processVideos() {
//        //ConsoleHelper.writeMessage("calling processVideos method");
//        //2.2. Подобрать список видео из доступных, просмотр которых обеспечивает максимальную выгоду. (Пока делать не нужно, сделаем позже).
//
//        //2.3. Если нет рекламных видео, которые можно показать посетителю, то бросить NoVideoAvailableException,
//        // которое перехватить в оптимальном месте (подумать, где это место) и
//        // с уровнем Level.INFO логировать фразу "No video is available for the order " + order
//
//        if (storage.list().isEmpty()) {
//            throw new NoVideoAvailableException();
//        } else {
//            //2.4. Отобразить все рекламные ролики, отобранные для показа, в порядке уменьшения стоимости показа одного рекламного
//            // ролика в копейках.
//            // Вторичная сортировка - по увеличению стоимости показа одной секунды рекламного ролика в тысячных частях копейки.
////Используйте метод Collections.sort
////(Тоже пока делать не нужно, сделаем позже).
////
////Пример для заказа [Water]:
////First Video is displaying... 50, 277
////где First Video - название рекламного ролика
////где 50 - стоимость показа одного рекламного ролика в копейках
////где 277 - стоимость показа одной секунды рекламного ролика в тысячных частях копейки (равно 0.277 коп)
////Используйте методы из класса Advertisement.
//
//        }
//
//
//    }



    //5. Чтобы тестировать данную функциональность, нужно добавить вызов processVideos метода у AdvertisementManager.
    //Очевидно, что этот метод должен вызываться во время создания заказа, а точнее - в параллельном режиме.
    //Заказ готовится в то время, как видео смотрится.
    //Добавьте вызов метода processVideos() в нужное место.
    //5. В методе createOrder класса Tablet должен быть создан новый AdvertisementManager и у него должен быть вызван метод processVideos.
    //P.S. Не забудь что время приготовления заказа считается в минутах, а время показа рекламы в секундах!

    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException {

        List<Advertisement> videos = storage.list();
        if (storage.list().isEmpty())
            throw new NoVideoAvailableException();

        // ищем список видео для показа согласно критериям
        List<Advertisement> bestAds = new VideoHelper().findAllYouNeed();

        // сортируем полученный список
        Collections.sort(bestAds, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement video1, Advertisement video2) {
                long dif = video2.getAmountPerOneDisplaying() - video1.getAmountPerOneDisplaying();
                if (dif == 0) dif = video2.getDuration() - video1.getDuration();
                return (int) dif;
            }
        });
        long amount = 0;
        int totalDuration = 0;
        for (Advertisement ad : bestAds) {
            totalDuration += ad.getDuration();
            amount += ad.getAmountPerOneDisplaying();
        }
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(bestAds, amount, totalDuration));
        //StatisticManager.getInstance().register(new VideoSelectedEventDataRow(videosToShow, totalAmount, totalDuration));

        // выводим список
        for (Advertisement ad : bestAds) {
            ConsoleHelper.writeMessage(ad.getName() + " is displaying... " +
                    ad.getAmountPerOneDisplaying() + ", " +
                    1000 * ad.getAmountPerOneDisplaying() / ad.getDuration());
            ad.revalidate();
        }
    }

    private class VideoHelper {
        private int bestPrice = 0;
        private int maxTime = 0;
        private int numberOfClips = 0;
        private List<Advertisement> bestAds = new ArrayList<>();
        private List<Advertisement> candidates = new ArrayList<>();

        public List<Advertisement> findAllYouNeed() {
            // отбор кандидатов
            for (Advertisement ad : storage.list()) {
                if (ad.getDuration() <= timeSeconds && ad.getHits() > 0)
                    candidates.add(ad);
            }
            if (candidates.isEmpty()) {
                throw new NoVideoAvailableException();
            } else findBestAds(new BinaryPattern(candidates.size()));
            return bestAds;
        }

        // рекурсивная функция формирования списка для показа
        public void findBestAds(BinaryPattern pattern) {
            while (true) {
                checkAds(pattern.getPattern());
                if (!pattern.full()) pattern.increment();
                else break;
                findBestAds(pattern);
            }
        }

        // проверка очередного набора видеоклипов
        private void checkAds(int[] pattern) {
            int price = 0;
            int time = 0;
            List<Advertisement> list = new ArrayList<>();
            for (int i = 0; i < candidates.size(); i++) {
                price += candidates.get(i).getAmountPerOneDisplaying() * pattern[i];
                time += candidates.get(i).getDuration() * pattern[i];
                if (pattern[i] == 1) list.add(candidates.get(i));
            }
            if (time > timeSeconds) return;
            if (!(price > bestPrice)) {
                if (!(price == bestPrice && time > maxTime)) {
                    if (!(price == bestPrice && time == maxTime && list.size() < numberOfClips)) {
                        return;
                    }
                }
            }
            bestAds = list;
            bestPrice = price;
            maxTime = time;
            numberOfClips = list.size();
        }

        // формирование двоичных масок для сбора списка видеоклипов
        private class BinaryPattern {
            private int length;
            private int count;

            public BinaryPattern(int size) {
                this.length = size;
                this.count = 0;
            }

            public int[] getPattern() {
                String regString = Integer.toBinaryString(count);
                int dif = length - regString.length();
                int[] pattern = new int[length];
                for (int j = dif; j < pattern.length; j++) {
                    if (regString.charAt(j - dif) == '1') pattern[j] = 1;
                    else pattern[j] = 0;
                }
                return pattern;
            }

            public void increment() {
                count++;
            }

            ;

            public boolean full() {
                return count == (int) Math.pow(2, length) - 1;
            }
        }
    }


}


//AdvertisementManager - у каждого планшета будет свой объект менеджера,
// который будет подбирать оптимальный набор роликов и их последовательность для каждого заказа.
//Он также будет взаимодействовать с плеером и отображать ролики.
