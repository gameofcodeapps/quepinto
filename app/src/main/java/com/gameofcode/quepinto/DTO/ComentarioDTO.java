package com.gameofcode.quepinto.DTO;

public class ComentarioDTO {

    private int id;
    private String comentario;
    private int idUsuario;
    private String fecha;
    private int idEvento;
    private String usuario;

    public ComentarioDTO(int id, String comentario, int idUsuario, String fecha, int idEvento, String usuario) {
        this.id = id;
        this.comentario = comentario;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.idEvento = idEvento;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
