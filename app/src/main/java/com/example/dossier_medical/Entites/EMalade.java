package com.example.dossier_medical.Entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_malade")
public class EMalade {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "ref")
    private String ref;
    @DatabaseField(columnName = "nom")
    private String nom;
    @DatabaseField(columnName = "postnom")
    private String postnom;
    @DatabaseField(columnName = "prenom")
    private String prenom;
    @DatabaseField(columnName = "sexe")
    private String sexe;
    @DatabaseField(columnName = "date_naissance")
    private String date_naissance;
    @DatabaseField(columnName = "groupe_sanguin")
    private String groupe_sanguin;
    @DatabaseField(columnName = "telephone")
    private String telephone;
    @DatabaseField(columnName = "email")
    private String email;
    @DatabaseField(columnName = "adresse")
    private String adresse;
    @DatabaseField(columnName = "etat_civil")
    private String etat_civil;
    @DatabaseField(columnName = "remarque")
    private String remarque;
    @DatabaseField(columnName = "url_photo")
    private String url_photo;
    @DatabaseField(columnName = "vaccin")
    private String vaccin;
    @DatabaseField(columnName = "tension")
    private String tension;
    @DatabaseField(columnName = "glycemie")
    private String glycemie;
    @DatabaseField(columnName = "date_create")
    private Long date_create;
    @DatabaseField(columnName = "date_update")
    private Long date_update;
   // @DatabaseField(columnName = "version")
    //private int __v;

    public EMalade() {
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPostnom() {
        return postnom;
    }

    public void setPostnom(String postnom) {
        this.postnom = postnom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getGroupe_sanguin() {
        return groupe_sanguin;
    }

    public void setGroupe_sanguin(String groupe_sanguin) {
        this.groupe_sanguin = groupe_sanguin;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEtat_civil() {
        return etat_civil;
    }

    public void setEtat_civil(String etat_civil) {
        this.etat_civil = etat_civil;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
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

    public String getVaccin() {
        return vaccin;
    }

    public void setVaccin(String vaccin) {
        this.vaccin = vaccin;
    }

    public String getTension() {
        return tension;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public String getGlycemie() {
        return glycemie;
    }

    public void setGlycemie(String glycemie) {
        this.glycemie = glycemie;
    }
}
