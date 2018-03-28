package vert.wiki;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2018/3/23.
 * Description :
 */
@DataObject(generateConverter = true)
public class Portfolio {

    private Map<String, Integer> shares = new TreeMap<>();

    private double cash;

    public Portfolio() {
    }

    public Portfolio(Portfolio other) {
        this.shares = new TreeMap<>(other.shares);
        this.cash = other.cash;
    }

    public Portfolio(JsonObject json) {
        //PortfolioConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        //PortfolioConverter.toJson(this, json);
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
}
