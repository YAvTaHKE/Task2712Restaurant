package main;

/*
        директор должен из какого-то места вызвать эти методы. Дадим ему планшет, но с другим ПО.
        Для этого создадим класс DirectorTablet, в котором будут дружелюбный интерфейс и возможность обращения к статистике.
*/

import main.ad.Advertisement;
import main.ad.StatisticAdvertisementManager;
import main.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DirectorTablet {


    //показывает какую сумму заработали на рекламе, сгруппировать по дням
    void printAdvertisementProfit(){
        Map<String, Double> map = StatisticManager.getInstance().getStatisticForShownAdvertisement();
        double totalAmount = 0;

        for (Map.Entry<String, Double> entry : map.entrySet())
        {
            totalAmount += entry.getValue();
            System.out.println(entry.getKey() + " - " + String.format("%.2f", entry.getValue()));
        }
        System.out.println(String.format("Total - %.2f", totalAmount));
    }

    //показывает загрузку (рабочее время) повара, сгруппировать по дням
    void printCookWorkloading(){
        Map<String, Map<String, Integer>> map = StatisticManager.getInstance().getStatisticForCooks();

        for (Map.Entry<String, Map<String, Integer>> entry1 : map.entrySet())
        {
            System.out.println(entry1.getKey());
            for (Map.Entry<String, Integer> entry2 : entry1.getValue().entrySet())
            {
                System.out.println(entry2.getKey() + " - " + entry2.getValue() + " min");
            }
        }

    }

    //показывает список активных роликов и оставшееся количество показов по каждому
    void printActiveVideoSet(){
        List<Advertisement> activeVideoSet = StatisticAdvertisementManager.getInstance().getVideoSet(true);
        for (Advertisement ad : activeVideoSet)
        {
            ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getHits());
        }
    }
    //показывает список неактивных роликов (с оставшемся количеством показов равным нулю)
    void printArchivedVideoSet(){
        List<Advertisement> activeVideoSet = StatisticAdvertisementManager.getInstance().getVideoSet(false);
        for (Advertisement ad : activeVideoSet)
        {
            ConsoleHelper.writeMessage(ad.getName());
        }
    }

}
