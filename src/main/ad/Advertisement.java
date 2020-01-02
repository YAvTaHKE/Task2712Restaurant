package main.ad;

//Класс рекламного ралика
public class Advertisement {
    public Object content;// видео
    String name;//  имя/название
    long initialAmount;//  начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    int hits;// количество оплаченных показов
    int duration;// продолжительность в секундах
    long amountPerOneDisplaying; //стоимость одного показа рекламного объявления в копейках (initialAmount/hits).

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

    public void revalidate() throws UnsupportedOperationException {
        if (getHits() <= 0)
            throw new NoVideoAvailableException();

        hits--;
    }

    public int getHits(){return hits;}
    public long getInitialAmount() {
        return initialAmount;
    }
}
