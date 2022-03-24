package com.example.dossier_medical.NetWork;


import com.example.dossier_medical.Entites.EServeur;
import com.example.dossier_medical.Memory.Constant;
import com.example.dossier_medical.Utils.UtilEServeur;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    static EServeur eServeur = UtilEServeur.getServeur();
    private static final String BASE_URL = Constant.HTTP+"://"+eServeur.getHOSTNAME()+"/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
