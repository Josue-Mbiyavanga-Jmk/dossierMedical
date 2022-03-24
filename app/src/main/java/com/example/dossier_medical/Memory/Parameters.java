package com.example.dossier_medical.Memory;


public class Parameters {


    //SERVER DE PRODUCTION
    //SERVER DE PRODUCTION
    private static final String BASE_URL = Config.ConfigSystem.SERV_PROD_HOST;
    public static String BASE_URL_PORT = Config.ConfigSystem.SERV_PROD_PORT;

    public static final int VOLLEY_RETRY_POLICY = 30000;

    //route link
    public static final String URL_REQUEST_TRANSACTION = "http://sycotax-dashboard-stg.us-east-1.elasticbeanstalk.com/api/transaction";
    /// URL ROOT
    public static final String URL_REQUEST_CATEGORIE =  "categorie/";
    public static final String URL_REQUEST_STATION =  "station/";
    public static final String URL_REQUEST_PRODUIT =  "produit/";
    public static final String URL_REQUEST_ENTREPRISE =  "entreprise/";
    public static final String URL_REQUEST_MALADE =  "malade/";
    public static final String URL_REQUEST_MEDECIN=  "medecin/";
    public static final String URL_REQUEST_LOAD =  "load/";
    /// URL FEUILLE
    public static final String TOKEN1 =  "TGEffNc43CZUhE4kL6Nv0nZCHekG5htZsC5zRDNFEDAeE5jLVFuVAIkrkM7uEQ0G8PxfFfp3kjJkHJcnthZPAWjyKUg7sDFghAwvRxdw19JG8i1AeNYyt10ukrBy6uDR";
    public static final String TOKEN = "sTQkYoj3QYS3XnyHP07E2xse6Lz28FjPDIhI6OpVW3u9jBbeNgBqQ8tXbslTs2e9vt2L4aDVWeA4zERfIuHi2zFvp5Q9ado3sQkDdHIdEaOqwEC7VYtHJ8F6DulM7uGo";
    public static final String V1_LOGIN =  "v1/authentification";
    public static final String V1_GETALL =  "v1/getAll";
    public static final String V1_ADD =  "v1/add";
    public static final String V1_UPDATE =  "v1/update";
    public static final String V1_ADD_CATEGORIE =  "v1/addCategorie";
    public static final String V1_GET_ALL_MIN_DATA =  "v1/getAllMinimalData";
    public static final String V1_GET_ALL_MAX_DATA =  "v1/getAllMaximalData";
    public static final String V1_GET_ALL_ENTREPRISE =  "v1/getAllEntreprise";
    public static final String V2_GET_ALL_ENTREPRISE =  "v2/getAllEntreprise";


    public static class Config {


        public static class ConfigSystem {
            //LOCALHOST
            static final String LOCAL_HOST = "http://10.195.48.111/";
            static String LOCAL_HOST_PORT = "http://10.195.48.111:80/";


            static final String SERV_PROD_HOST = "http://vps63737.lws-hosting.com/";
            static final String SERV_PROD_PORT = "http://vps63737.lws-hosting.com:8090/";


            public  static String IP(String ip, String port, String protole)
            {
                return   LOCAL_HOST_PORT=protole+"://"+ip+":"+port+"/";
            }
        }


        //FTP DIRECTORY
        public static class DirectoryFTP {

        }
        //URL SERVICES
        public static class URL_SERVICE {
            public static final String URL_SERVICE_HOST = "dgrhu_ws/api/services/";

        }
    }



}