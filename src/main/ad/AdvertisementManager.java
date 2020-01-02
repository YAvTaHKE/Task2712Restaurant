package main.ad;

import main.ConsoleHelper;
import main.statistic.StatisticManager;
import main.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.List;

//AdvertisementManager - у каждого планшета будет свой объект менеджера,
// который будет подбирать оптимальный набор роликов и их последовательность для каждого заказа.
//Он также будет взаимодействовать с плеером и отображать ролики.

public class AdvertisementManager {

    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    //Время выполнения заказа в секундах
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    /*
2.2. Подобрать список видео из доступных, просмотр которых обеспечивает максимальную выгоду.

2.3. Если нет рекламных видео, которые можно показать посетителю, то бросить NoVideoAvailableException,
которое перехватить в оптимальном месте (подумать, где это место) и
с уровнем Level.INFO логировать фразу "No video is available for the order " + order

2.4. Отобразить все рекламные ролики, отобранные для показа, в порядке уменьшения стоимости показа одного рекламного
 ролика в копейках. Вторичная сортировка - по увеличению стоимости показа одной секунды рекламного ролика в тысячных частях копейки.
     */

    public void processVideos() throws NoVideoAvailableException {

        if (storage.list().isEmpty())
            throw new NoVideoAvailableException();

        // ищем список видео для показа согласно критериям
        List<Advertisement> bestAds = new VideoHelper().findAllYouNeed();

        // сортируем полученный список
        bestAds.sort((video1, video2) -> {
            long dif = video2.getAmountPerOneDisplaying() - video1.getAmountPerOneDisplaying();
            if (dif == 0) dif = video2.getDuration() - video1.getDuration();
            return (int) dif;
        });
        long amount = 0;
        int totalDuration = 0;
        for (Advertisement ad : bestAds) {
            totalDuration += ad.getDuration();
            amount += ad.getAmountPerOneDisplaying();
        }
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(bestAds, amount, totalDuration));

        // выводим список
        // Пример для заказа [Water]:
        //First Video is displaying... 50, 277
        //где First Video - название рекламного ролика
        //где 50 - стоимость показа одного рекламного ролика в копейках
        //где 277 - стоимость показа одной секунды рекламного ролика в тысячных частях копейки (равно 0.277 коп)

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

        List<Advertisement> findAllYouNeed() {
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
        void findBestAds(BinaryPattern pattern) {
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

            BinaryPattern(int size) {
                this.length = size;
                this.count = 0;
            }

            int[] getPattern() {
                String regString = Integer.toBinaryString(count);
                int dif = length - regString.length();
                int[] pattern = new int[length];
                for (int j = dif; j < pattern.length; j++) {
                    if (regString.charAt(j - dif) == '1') pattern[j] = 1;
                    else pattern[j] = 0;
                }
                return pattern;
            }

            void increment() {
                count++;
            }

            boolean full() {
                return count == (int) Math.pow(2, length) - 1;
            }
        }
    }
}
