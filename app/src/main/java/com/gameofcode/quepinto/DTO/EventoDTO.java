package com.gameofcode.quepinto.DTO;

import android.graphics.Bitmap;

import java.io.Serializable;

public class EventoDTO implements Serializable {

    private int id;
    private String nombreEvento;
    private String organizador;
    private String categoria;
    private String descripcion;
    private String imagenEvento;
    private String ciudad;
    private String departamento;
    private String pais;
    private String fechaInicio;
    private String fechaFin;
    private String horaFin;
    private String horaInicio;
    private String latitud;
    private String direccion;
    private String longitud;
    private String usuarioCreador;
    ////Alternativa a cargar imagen
    ///Lo uso para grabar la imagen y convertirla en base64
    private Bitmap imagenEventoBMP;
    /////////////////////////////////
    public EventoDTO(int id, String nombreEvento, String organizador, String categoria, String descripcion, String imagenEvento, String ciudad, String departamento, String pais, String fechaInicio, String fechaFin, String horaFin, String horaInicio, String latitud, String direccion, String longitud, String usuarioCreador) {
        this.id = id;
        this.nombreEvento = nombreEvento;
        this.organizador = organizador;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.imagenEvento = imagenEvento;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.pais = pais;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaFin = horaFin;
        this.horaInicio = horaInicio;
        this.latitud = latitud;
        this.direccion = direccion;
        this.longitud = longitud;
        this.usuarioCreador = usuarioCreador;
    }

    public EventoDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenEvento() {
        return imagenEvento;
    }

    public void setImagenEvento(String imagenEvento) {
        this.imagenEvento = imagenEvento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }


    public Bitmap getImagenEventoBMP() {
        return imagenEventoBMP;
    }

    public void setImagenEventoBMP(Bitmap imagenEventoBMP) {
        this.imagenEventoBMP = imagenEventoBMP;
    }
    ///////////////////////////
}
