package com.example.dossier_medical.Implements;



import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Memory.Parameters;
import com.example.dossier_medical.Utils.GenericObjet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IHttpRestrofit {
    @FormUrlEncoded
    @POST(Parameters.V1_GETALL) //url
    Call<GenericObjet<EMalade>> synchroniseStation(@Field("malade") String station
    );
}
