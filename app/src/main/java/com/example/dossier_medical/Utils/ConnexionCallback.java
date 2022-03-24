package com.example.dossier_medical.Utils;

import org.json.JSONObject;

public interface ConnexionCallback {

    void errorJson(JSONObject errorResponsejson);
    void onError(String errorResponse);
}
