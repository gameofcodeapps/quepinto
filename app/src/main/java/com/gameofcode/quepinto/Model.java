package com.gameofcode.quepinto;

import android.graphics.Bitmap;

public class Model{
    private String header;
    private String fecha;
    private String txtmapa;
    private String organizador;
    private String desc;
    private int imgname;
    private String urlimagen;
    private boolean esFavorito;

    public boolean isEsFavorito() {
        return esFavorito;
    }

    public void setEsFavorito(boolean esFavorito) {
        this.esFavorito = esFavorito;
    }



////Alternativa a cargar imagen/////
 /*   private Bitmap imgen;
    public Bitmap getImgen() {
        return imgen;
    }

    public void setImgen(Bitmap imgen) {
        this.imgen = imgen;
    }*/
///////////////////////////////////////

    public String getUrlimagen() {return urlimagen;}

    public void setUrlimagen(String urlimagen) {this.urlimagen = urlimagen;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;


    public int getImgname() {
        return imgname;
    }

    public void setImgname(int imgname) {
        this.imgname = imgname;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getFecha() { return fecha; }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

   public String getTxtmapa() {
        return txtmapa;
    }

    public void setTxtmapa(String txtmapa) {
        this.txtmapa = txtmapa;
    }

}