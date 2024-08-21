package vn.edu.eaut.unitconverter.model.converter;

import android.content.Context;

import vn.edu.eaut.unitconverter.model.nbrb.NBRBRepository;

public class CurrencyConverter extends Converter{

    public CurrencyConverter(Context context) {
        super(context);
    }

    @Override
    public void load() throws Exception {
        
    }

    public void setRepository(NBRBRepository repository) {
    }
}
