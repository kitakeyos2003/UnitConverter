package vn.edu.eaut.unitconverter.api;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "Exrate", strict = false)
public class Exrate {

    @Attribute(name = "CurrencyCode")
    private String currencyCode;

    @Attribute(name = "CurrencyName")
    private String currencyName;

    @Attribute(name = "Buy")
    private String buy;

    @Attribute(name = "Transfer")
    private String transfer;

    @Attribute(name = "Sell")
    private String sell;

    public Exrate() {

    }

    public Exrate(String currencyCode, String currencyName, String buy, String transfer, String sell) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.buy = buy;
        this.transfer = transfer;
        this.sell = sell;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}

