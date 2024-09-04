package vn.edu.eaut.unitconverter.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class HistoryEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "unit_from")
    private String unitFrom;

    @ColumnInfo(name = "unit_to")
    private String unitTo;

    @ColumnInfo(name = "value_from")
    private String valueFrom;

    @ColumnInfo(name = "value_to")
    private String valueTo;

    @ColumnInfo(name = "category")
    private int category;

    public HistoryEntity(int id, String unitFrom, String unitTo, String valueFrom, String valueTo, int category) {
        this.id = id;
        this.unitFrom = unitFrom;
        this.unitTo = unitTo;
        this.valueFrom = valueFrom;
        this.valueTo = valueTo;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitFrom() {
        return unitFrom;
    }

    public void setUnitFrom(String unitFrom) {
        this.unitFrom = unitFrom;
    }

    public String getUnitTo() {
        return unitTo;
    }

    public void setUnitTo(String unitTo) {
        this.unitTo = unitTo;
    }

    public String getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
