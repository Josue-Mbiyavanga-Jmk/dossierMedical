package com.example.dossier_medical.Utils;

import org.json.JSONObject;

/**
 * Created by Kevin Lukanga on 28/05/2018.
 */

public interface HttpCallback {
    void onSuccess(JSONObject response);
    void onError(String message);
}
