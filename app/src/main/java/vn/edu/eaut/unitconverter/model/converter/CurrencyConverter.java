package vn.edu.eaut.unitconverter.model.converter;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.api.Exrate;
import vn.edu.eaut.unitconverter.api.ExrateList;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

public class CurrencyConverter extends Converter {

    private ExrateList exrateList;

    public CurrencyConverter(Context context) {
        super(context);
    }

    public void setData(ExrateList exrateList) {
        this.exrateList = exrateList;
    }

    @Override
    public void load() throws Exception {
        registerUnit(
                new ConversionUnit(
                        "Việt Nam Đồng",
                        1,
                        "VNĐ"
                )

        );
        if (exrateList != null) {
            for (Exrate exrate : exrateList.getExrates()) {
                registerUnit(new ConversionUnit(exrate.getCurrencyName(), Double.parseDouble(exrate.getTransfer().replaceAll(",", "")), exrate.getCurrencyCode()));
            }
        } else {
            Toast.makeText(context, context.getString(R.string.no_exrate_list), Toast.LENGTH_SHORT).show();
        }
    }
}
