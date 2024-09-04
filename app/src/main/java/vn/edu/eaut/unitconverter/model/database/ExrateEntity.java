package vn.edu.eaut.unitconverter.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exrates")
public class ExrateEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "code")
    private String currencyCode;
    @ColumnInfo(name = "name")
    private String currencyName;
    @ColumnInfo(name = "buy")
    private String buy;
    @ColumnInfo(name = "transfer")
    private String transfer;
    @ColumnInfo(name = "sell")
    private String sell;

    public ExrateEntity(int id, String currencyCode, String currencyName, String buy, String transfer, String sell) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.buy = buy;
        this.transfer = transfer;
        this.sell = sell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }
}