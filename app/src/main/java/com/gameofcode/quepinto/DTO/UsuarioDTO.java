package com.gameofcode.quepinto.DTO;

import java.util.List;

public class UsuarioDTO {

    private String id;
    private String password;
    private String lastLogin;
    private String isSuperUser;
    private String username;
    private String firstName;
    private String email;
    private String isStaff;
    private String isActive;
    private String dateJoined;
    private String lastName;



    private List<EventoDTO> eventoFavoritos;

    public UsuarioDTO() {
        this.email="";
    }

    public UsuarioDTO(String password, String username, String firstName, String email, String lastName) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
    }

    public UsuarioDTO(String id, String password, String lastLogin, String isSuperUser, String username, String firstName, String email, String isStaff, String isActive, String dateJoined, String lastName) {
        this.id = id;
        this.password = password;
        this.lastLogin = lastLogin;
        this.isSuperUser = isSuperUser;
        this.username = username;
        this.firstName = firstName;
        this.email = email;
        this.isStaff = isStaff;
        this.isActive = isActive;
        this.dateJoined = dateJoined;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getIsSuperUser() {
        return isSuperUser;
    }

    public void setIsSuperUser(String isSuperUser) {
        this.isSuperUser = isSuperUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<EventoDTO> getEventoFavoritos() {
        return eventoFavoritos;
    }

    public void setEventoFavoritos(List<EventoDTO> eventoFavoritos) {
        this.eventoFavoritos = eventoFavoritos;
    }
}
