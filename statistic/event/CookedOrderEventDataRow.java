package main.statistic.event;

import main.kitchen.Dish;

import java.util.Date;
import java.util.List;

public class CookedOrderEventDataRow implements EventDataRow {
    //где - tabletName - имя планшета
    //cookName - имя повара
    //cookingTimeSeconds - время приготовления заказа в секундах
    //cookingDishs - список блюд для приготовления

    String tabletName;
    private String cookName;
    int cookingTimeSeconds;
    List<Dish> cookingDishs;
    Date currentDate;

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishs) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishs = cookingDishs;
        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }

    public String getCookName(){
        return this.cookName;
    }
}




