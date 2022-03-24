package com.example.dossier_medical.Entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_tension")
public class ETension {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "ref")
    private String ref;
    @DatabaseField(columnName = "systolique")
    private String systolique;
    @DatabaseField(columnName = "diastolique")
    private String diastolique;
    @DatabaseField(columnName = "pouls")
    private String pouls;
    @DatabaseField(columnName = "date")
    private String date;
    @DatabaseField(columnName = "heure")
    private String heure;
    @DatabaseField(columnName = "status")
    private int status;
    @DatabaseField(columnName = "medecin_create")
    private String medecin_create;
    @DatabaseField(columnName = "date_create")
    private Long date_create;
    @DatabaseField(columnName = "date_update")
    private Long date_update;


    public ETension() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSystolique() {
        return systolique;
    }

    public void setSystolique(String systolique) {
        this.systolique = systolique;
    }

    public String getDiastolique() {
        return diastolique;
    }

    public void setDiastolique(String diastolique) {
        this.diastolique = diastolique;
    }

    public String getPouls() {
        return pouls;
    }

    public void setPouls(String pouls) {
        this.pouls = pouls;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedecin_create() {
        return medecin_create;
    }

    public void setMedecin_create(String medecin_create) {
        this.medecin_create = medecin_create;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getDate_create() {
        return date_create;
    }

    public void setDate_create(Long date_create) {
        this.date_create = date_create;
    }

    public Long getDate_update() {
        return date_update;
    }

    public void setDate_update(Long date_update) {
        this.date_update = date_update;
    }

}
