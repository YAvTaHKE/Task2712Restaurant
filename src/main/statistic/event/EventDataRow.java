package main.statistic.event;

import java.util.Date;

public interface EventDataRow {
    //получить тип события
    EventType getType();
    //получить дату создания записи
    Date getDate();
    //получить время - продолжительность
    int getTime();
}
