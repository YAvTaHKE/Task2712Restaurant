package main.ad;

public class Advertisement {
    public Object content;// видео
    String name;//  имя/название
    long initialAmount;//  начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    int hits;// количество оплаченных показов
    int duration;// продолжительность в секундах
    //Модификаторы доступа расставь самостоятельно.
    long amountPerOneDisplaying;
    //1. В классе Advertisement создай поле long amountPerOneDisplaying.
    //Оно должно равняться стоимости одного показа рекламного объявления в копейках (initialAmount/hits).
    //Присвой значение полю в конструкторе.


    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        this.amountPerOneDisplaying = (hits > 0) ? initialAmount / hits : 0;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public void revalidate() throws
            UnsupportedOperationException {
        if (getHits() <= 0)
            //throw new UnsupportedOperationException();
            throw new NoVideoAvailableException();

        hits--;
    }


    public int getHits(){return hits;}
    public long getInitialAmount() {
        return initialAmount;
    }


}
