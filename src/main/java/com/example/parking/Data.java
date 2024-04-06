package com.example.parking;

public class Data {

    private String Nom;

    private String Prenom;

    private String Email;
    private String Password;
    private String Tel;

    public Data(String nom, String prenom, String email, String password, String tel) {
        Nom = nom;
        Prenom = prenom;
        Email = email;
        Password = password;
        Tel = tel;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }
}
