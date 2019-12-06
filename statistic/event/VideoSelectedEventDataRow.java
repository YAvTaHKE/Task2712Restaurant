package main.statistic.event;

import main.ad.Advertisement;

import java.util.Date;
import java.util.List;

//Событие при выборе списка видео для показа
public class VideoSelectedEventDataRow implements EventDataRow {

    private  List<Advertisement> optimalVideoSet; //список видео-роликов, отобранных для показа
    private long amount;   //сумма денег в копейках
    private int totalDuration; //общая продолжительность показа отобранных рекламных роликов
    private Date currentDate;

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration ) {
        this.optimalVideoSet = optimalVideoSet;
        this.amount = amount;
        this.totalDuration = totalDuration;
        this.currentDate = new Date();
    }

    public List<Advertisement> getOptimalVideoSet() {
        return optimalVideoSet;
    }

    @Override
    public EventType getType() {
        return EventType.SELECTED_VIDEOS;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return totalDuration;
    }

    public long getAmount() {
        return amount;
    }
}
