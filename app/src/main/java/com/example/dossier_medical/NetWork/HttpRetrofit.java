package com.example.dossier_medical.NetWork;


import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Implements.IHttpRestrofit;
import com.example.dossier_medical.Utils.GenericObjet;

import java.io.IOException;

import retrofit2.Call;

public class HttpRetrofit {

    public  static GenericObjet<EMalade> addStation (String... param){
        IHttpRestrofit service=ServiceGenerator.createService(IHttpRestrofit.class);
        Call<GenericObjet<EMalade>> call= service.synchroniseStation(param[0]);
        GenericObjet<EMalade> rst=null;
        try {
            rst=call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rst;
    }



}
