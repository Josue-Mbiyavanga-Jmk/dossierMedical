package com.example.dossier_medical.Utils;

/**
 * Created by AdbY on 28/05/2018.
 */

public interface HttpCallbackString {
    void onSuccess(String response);
    void onError(String message);
}
