package com.example.dossier_medical.NetWork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dossier_medical.Entites.EServeur;
import com.example.dossier_medical.Memory.Constant;
import com.example.dossier_medical.Memory.Parameters;
import com.example.dossier_medical.R;
import com.example.dossier_medical.Utils.HttpCallback;
import com.example.dossier_medical.Utils.HttpCallbackJSON;
import com.example.dossier_medical.Utils.HttpCallbackString;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.example.dossier_medical.Memory.Parameters.Config.ConfigSystem.IP;


/**
 * Created by kevin lukanga on 28/05/2019.
 */

public class HttpRequest {


    private static boolean isConnected(Context context) {
        boolean b=false;
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                b= networkInfo != null && networkInfo.isConnected();
            }

        } catch (Exception e) {

            return false;
        }

        return b;
    }

    ///chargement
    public static void loadAllEntreprise(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_LOAD+Parameters.V2_GET_ALL_ENTREPRISE,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("uid", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ///Categorie
    //chargement de toutes les catégories
    public static void loadCategories(Context context, EServeur eServeur, HttpCallbackJSON callbackJSON) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    //IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_LOAD+Parameters.V1_GET_ALL_MIN_DATA,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_CATEGORIE+Parameters.V1_GETALL,
                    null,
                    requestSuccessListener(callbackJSON),
                    requestErrorListener(context,callbackJSON));

            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///Entreprise
    public static void addEntreprise(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_ENTREPRISE+Parameters.V1_ADD,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("entreprise", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addMalade(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_MALADE+Parameters.V1_ADD,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("malade", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addMedecin(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_MEDECIN+Parameters.V1_ADD,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("medecin", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateEntreprise(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_ENTREPRISE+Parameters.V1_UPDATE,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("entreprise", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Ajout Catégorie Entreprise
    public static void addCategorieEntreprise(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_ENTREPRISE+Parameters.V1_ADD_CATEGORIE,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", param[0]);
                    params.put("categorie", param[1]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Authentification user de l'entreprise
    public static void loginUser(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_ENTREPRISE+Parameters.V1_LOGIN,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("pseudo", param[0]);
                    params.put("password", param[1]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///Produit
    public static void addProduit(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_PRODUIT+Parameters.V1_ADD,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("produit", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateProduit(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_PRODUIT+Parameters.V1_UPDATE,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("produit", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getAllProduit(Context context, EServeur eServeur,HttpCallbackJSON callbackJSON) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_PRODUIT+Parameters.V1_GETALL,
                    null,
                    requestSuccessListener(callbackJSON),
                    requestErrorListener(context,callbackJSON));

            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    ///Station
    public static void addStation(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_STATION+Parameters.V1_ADD,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("station", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateStation(Context context, EServeur eServeur, final String[] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_STATION+Parameters.V1_UPDATE,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("station", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllStation(Context context, EServeur eServeur,HttpCallbackJSON callbackJSON) {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_STATION+Parameters.V1_GETALL,
                    null,
                    requestSuccessListener(callbackJSON),
                    requestErrorListener(context,callbackJSON));

            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    //GET requete modèle
    private static void load(Context context, EServeur eServeur,HttpCallbackJSON callbackJSON){


    }
   // raw body
    public static void addTransaction(Context context, final String[] param, HttpCallbackString callback) {
         try{
        StringRequest myReq = new StringRequest(Request.Method.POST,
                Parameters.URL_REQUEST_TRANSACTION,
                requestSuccessListener(callback),
                requestErrorListener(context,callback)) {

            @Override
            public byte[] getBody() throws com.android.volley.AuthFailureError {
                return param[0] == null ? null :  param[0].getBytes(StandardCharsets.UTF_8);
            };
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("X-API-Key", Parameters.TOKEN);
                header.put("content-type", "application/json");
                return header;
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }

        };

        MySingleton.getInstance(context).addToRequestQueue(myReq);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //succes
    private static Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.i(TAG,"Ski data from server - "+response);
            }
        };
    }
    //error
    private static Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.i(TAG,"Ski error connect - "+error);
            }
        };
    }

    private static Response.Listener<String> requestSuccessListener(final HttpCallback callback) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject data;

                        if(response.equals("[]"))
                        {
                            data=new JSONObject();
                            data.put("empty","1");
                        }
                        else
                        {
                            JSONArray jsonArray =new JSONArray(response);
                            data=jsonArray.getJSONObject(0);
                        }

                        try {

                        } catch (IllegalStateException e) {
                            String messageError = "Read JSON response Error !!!";
                            callback.onError(messageError);
                        }
                        callback.onSuccess(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private static Response.Listener<String> requestSuccessListener(final HttpCallbackString callback) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        callback.onSuccess(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }



    private static Response.Listener<JSONObject> requestSuccessListener(final HttpCallbackJSON callback) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        callback.onSuccess(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
    private static Response.ErrorListener requestErrorListener(final Context mContext, final HttpCallbackString connexionCallback) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                // For AuthFailure, you can re login with user credentials.
                // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                // In this case you can check how client is forming the api and debug accordingly.
                // For ServerError 5xx, you can do retry or handle accordingly.

                error.printStackTrace();

                String errorMessage = "";
                if (error instanceof NetworkError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_network_error);
                } else if (error instanceof ServerError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_server_error);
                } else if (error instanceof AuthFailureError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_auth_error);
                } else if (error instanceof ParseError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_parse_error);
                } else if (error instanceof NoConnectionError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_no_connection_error);
                } else if (error instanceof TimeoutError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_timeout_error);
                }


                connexionCallback.onError(errorMessage);

            }
        };
    }

    private static Response.ErrorListener requestErrorListener(final Context mContext, final HttpCallbackJSON connexionCallback) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                // For AuthFailure, you can re login with user credentials.
                // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                // In this case you can check how client is forming the api and debug accordingly.
                // For ServerError 5xx, you can do retry or handle accordingly.

                error.printStackTrace();

                String errorMessage = "";
                if (error instanceof NetworkError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_network_error);
                } else if (error instanceof ServerError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_server_error);
                } else if (error instanceof AuthFailureError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_auth_error);
                } else if (error instanceof ParseError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_parse_error);
                } else if (error instanceof NoConnectionError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_no_connection_error);
                } else if (error instanceof TimeoutError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_timeout_error);
                }


                connexionCallback.onError(errorMessage);

            }
        };
    }
}
