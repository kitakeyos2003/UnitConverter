package vn.edu.eaut.unitconverter.model;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.util.concurrent.atomic.AtomicInteger;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.model.converter.AreaConverter;
import vn.edu.eaut.unitconverter.model.converter.Converter;
import vn.edu.eaut.unitconverter.model.converter.CurrencyConverter;
import vn.edu.eaut.unitconverter.model.converter.LengthConverter;
import vn.edu.eaut.unitconverter.model.converter.MassConverter;
import vn.edu.eaut.unitconverter.model.converter.MemoryConverter;
import vn.edu.eaut.unitconverter.model.converter.OtherConverter;
import vn.edu.eaut.unitconverter.model.converter.SpeedConverter;
import vn.edu.eaut.unitconverter.model.converter.TemperatureConverter;
import vn.edu.eaut.unitconverter.model.converter.TimeConverter;
import vn.edu.eaut.unitconverter.model.converter.VolumeConverter;

public enum ConverterCategory {
    MASS(0,
            R.string.mass,
            R.drawable.ic_weight,
            R.color.material_red_500,
            MassConverter::new
    ),
    VOLUME(1,
            R.string.volume,
            R.drawable.ic_volume,
            R.color.material_green_accent_700,
            VolumeConverter::new
    ),
    TEMPERATURE(2,
            R.string.temperature,
            R.drawable.ic_temperature,
            R.color.material_purple_500,
            TemperatureConverter::new
    ),
    SPEED(3,
            R.string.speed,
            R.drawable.ic_speed,
            R.color.material_indigo_500,
            SpeedConverter::new
    ),
    LENGTH(4,
            R.string.length,
            R.drawable.ic_ruler,
            R.color.material_bluegrey_500,
            LengthConverter::new
    ),
    AREA(5,
            R.string.area,
            R.drawable.ic_area,
            R.color.material_teal_500,
            AreaConverter::new
    ),
    MEMORY(6,
            R.string.memory,
            R.drawable.ic_memory,
            R.color.material_blue_500,
            MemoryConverter::new
    ),
    TIME(7,
            R.string.time,
            R.drawable.ic_timer,
            R.color.material_orange_500,
            TimeConverter::new
    ),
    CURRENCY(8,
            R.string.currency,
            R.drawable.ic_currency_usd,
            R.color.material_green_800,
            CurrencyConverter::new
    ),
    OTHER(9,
            R.string.other,
            R.drawable.ic_other,
            R.color.material_deep_purple_500,
            OtherConverter::new
    );

    @StringRes
    private final int categoryName;
    @DrawableRes
    private final int icon;
    @ColorRes
    private final int color;
    private final ConverterCreator creator;
    public final int id;

    ConverterCategory(int id, @StringRes int categoryName, @DrawableRes int icon, @ColorRes int color, ConverterCreator creator) {
        this.categoryName = categoryName;
        this.icon = icon;
        this.color = color;
        this.creator = creator;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCategoryName() {
        return categoryName;
    }

    public int getIcon() {
        return icon;
    }

    public int getColor() {
        return color;
    }

    public Converter getConverter(Context context) {
        return creator.create(context);
    }

    public interface ConverterCreator {
        Converter create(Context context);
    }
}
