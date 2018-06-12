package vert.wiki;

import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2018/3/23.
 * Description :
 */
public class Portfolio {

    private Map<String, Integer> shares = new TreeMap<>();

    private double cash;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.ISO_INSTANT, locale = "zh", timezone = "GMT+8")
    private Instant date;


    public Portfolio() {
    }

    public Portfolio(Portfolio other) {
        this.shares = new TreeMap<>(other.shares);
        this.cash = other.cash;
        this.date = other.date;
    }

    public Portfolio(JsonObject json) {
        PortfolioConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PortfolioConverter.toJson(this, json);
        return json;
    }

    public Map<String, Integer> getShares() {
        return shares;
    }


    public Portfolio setShares(Map<String, Integer> shares) {
        this.shares = shares;
        return this;
    }

    public double getCash() {
        return cash;
    }

    public Portfolio setCash(double cash) {
        this.cash = cash;
        return this;
    }
    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    // -- Additional method

    /**
     * This method is just a convenient method to get the number of owned shares of the specify company (name of the
     * company).
     *
     * @param name the name of the company
     * @return the number of owned shares, {@literal 0} is none.
     */
    public int getAmount(String name) {
        Integer current = shares.get(name);
        if (current == null) {
            return 0;
        }
        return current;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "shares=" + shares +
                ", cash=" + cash +
                ", date=" + date +
                '}';
    }
}
