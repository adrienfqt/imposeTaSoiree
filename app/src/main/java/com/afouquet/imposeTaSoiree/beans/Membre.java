package com.afouquet.imposeTaSoiree.beans;

import java.security.KeyStore;
import java.util.Date;

public class Membre {
    private String nom;
    private String prenom;
    private Date ddn;
    private String mail;
    private String login;
    private String password;

    public Membre(String nom, String prenom, Date ddn, String mail, String login, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.ddn = ddn;
        this.mail = mail;
        this.login = login;
        this.password = password;
    }

    public Membre(String nom, String prenom, Date ddn, String mail, String login) {
        this.nom = nom;
        this.prenom = prenom;
        this.ddn = ddn;
        this.mail = mail;
        this.login = login;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDdn(Date ddn) {
        this.ddn = ddn;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDdn() {
        return ddn;
    }

    public String getMail() {
        return mail;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return this.nom+" " + this.prenom+" - " + this.login;
    }
}
