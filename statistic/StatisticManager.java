package main.statistic;

import main.statistic.event.CookedOrderEventDataRow;
import main.statistic.event.EventDataRow;
import main.statistic.event.EventType;
import main.statistic.event.VideoSelectedEventDataRow;


import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {

    private static StatisticManager ourInstance = new StatisticManager();
    private StatisticStorage statisticStorage = new StatisticStorage();


    public static StatisticManager getInstance() {
        return ourInstance;
    }

    private StatisticManager() {
    }


    public void register(EventDataRow data){
        //5. В StatisticManager создадим публичный метод void register(EventDataRow data),
        // который будет регистрировать событие в хранилище.
        //Хранилище связано 1 к 1 с менеджером, т.е. один менеджер и одно хранилище на приложение.
        //К хранилищу может доступиться только StatisticManager. Поэтому...
        //Из вышеперечисленного следует, что хранилище должно быть приватным иннер классом.
        //Назовем его StatisticStorage.
        statisticStorage.put(data);
    }

    private static class StatisticStorage{

        //Статистика по всем событиям
        private Map<EventType, List<EventDataRow>> storage ; // КАРТА!!!!!!!

        public StatisticStorage( ) {
            storage =  new HashMap<>();
            for (EventType eventType: EventType.values()
            ) {
                storage.put(eventType,new ArrayList<EventDataRow>());
            }
            //4. В конструкторе StatisticStorage инициализируй хранилище данными по-умолчанию:
            //например используя цикл, для каждого EventType добавь new ArrayList<EventDataRow>()
        }
        private void put(EventDataRow data){
            storage.get(data.getType()).add(data); // добавляет поле data типа EventDataRow согласно одному из трех EventDataRow
        }

        private List<EventDataRow> getStorage(EventType eventType) {
            return storage.get(eventType);
        }
    }

    /*16.2 В StatisticManager создай метод (придумать самостоятельно), который из хранилища достанет все данные, относящиеся к отображению рекламы, и посчитает общую прибыль за каждый день.
Дополнительно добавь вспомогательный метод get в класс хранилища, чтобы получить доступ к данным.
*/

    //Метод получает данные по прибыли и сортирует по дням в убывающем порядке
    public Map<String, Double> getStatisticForShownAdvertisement() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Map<EventType, List<EventDataRow>> storageMap = statisticStorage.storage;
        List<EventDataRow> list = storageMap.get(EventType.SELECTED_VIDEOS);

        Map<String, Double> map = new TreeMap<>(Collections.reverseOrder());

        for (EventDataRow event : list)
        {
            VideoSelectedEventDataRow videoSelectedEvent = (VideoSelectedEventDataRow) event;
            String date = dateFormat.format(videoSelectedEvent.getDate());
            double amount = (double) videoSelectedEvent.getAmount() / 100;

            if (map.containsKey(date))
            {
                map.put(date, map.get(date) + amount);
            } else
            {
                map.put(date, amount);
            }
        }
        return map;
    }

    //Метод получает статистику по поворам и сортирует ее
    public Map<String, Map<String, Integer>> getStatisticForCooks() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Map<EventType, List<EventDataRow>> storageMap = statisticStorage.storage;
        List<EventDataRow> list = storageMap.get(EventType.COOKED_ORDER);

        Map<String, Map<String, Integer>> map = new TreeMap<>(Collections.reverseOrder());

        for (EventDataRow event : list)
        {
            CookedOrderEventDataRow cookedOrderEvent = (CookedOrderEventDataRow) event;
            String date = dateFormat.format(cookedOrderEvent.getDate());
            String cookName = cookedOrderEvent.getCookName();
            int cookingTime = cookedOrderEvent.getTime();
            int cookingTimeMin = (cookingTime % 60 == 0) ? (cookingTime / 60) : (cookingTime / 60 + 1);

            if (map.containsKey(date))
            {
                Map<String, Integer> temp = map.get(date);
                if (temp.containsKey(cookName))
                {
                    temp.put(cookName, temp.get(cookName) + cookingTimeMin);
                } else
                {
                    temp.put(cookName, cookingTimeMin);
                }
                map.put(date, temp);
            } else
            {
                Map<String, Integer> temp = new TreeMap<>();
                temp.put(cookName, cookingTimeMin);
                map.put(date, temp);
            }
        }
        return map;
    }
}
