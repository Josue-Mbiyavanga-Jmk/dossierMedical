package com.example.dossier_medical.Utils;

import java.util.List;

public class GenericObjet<T> {

    private   int result; // code réponse http
    private List<T> listStations; // data : un table Json qu'on retourne ensemble avec result
    private T station; // objet json qu'on retourne ensemble avec result

    public GenericObjet() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<T> getListStations() {
        return listStations;
    }

    public void setListStations(List<T> listStations) {
        this.listStations = listStations;
    }

    public T getStation() {
        return station;
    }

    public void setStation(T station) {
        this.station = station;
    }
}
