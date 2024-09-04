package vn.edu.eaut.unitconverter.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VietcombankApi {

    @GET("Usercontrols/TVPortal.TyGia/pXML.aspx?b=10")
    Call<ExrateList> getExrateList();
}
