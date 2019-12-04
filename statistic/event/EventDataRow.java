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
//4. Создадим в пакете event интерфейс EventDataRow. На данный момент он является интерфейсом-маркером, т.к.
// не содержит методов, и по нему мы определяем, является ли переданный объект событием или нет.
