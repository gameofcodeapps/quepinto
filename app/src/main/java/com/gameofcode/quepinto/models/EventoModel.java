package com.gameofcode.quepinto.models;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventoModel {


    private static EventoModel instance = null;

    public  EventoModel(){

    }

    public static EventoModel getInstance(){
        if(instance==null){
            instance = new EventoModel();
        }
        return instance;
    }

    public List<EventoDTO> obtenerTodosLosEventosHabilitados(){
        String sql = "SELECT * FROM home_evento";
        return obtenerEventos(sql);
    }

    public List<EventoDTO> obtenerEventosCreadosPorUsuarioLogeado(){
        String sql = "SELECT * from home_evento where user_owner="+UsuarioModel.getInstance().getUsuarioLogeado().getUsername();
        return obtenerEventos(sql);
    }

    private List<EventoDTO> obtenerEventos(String pSQL){

        ResultSet resultSet = null;
        List<EventoDTO> eventos = new ArrayList<EventoDTO>();
        try {
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(pSQL);
            while (resultSet.next()) {
                EventoDTO eventoDTO = new EventoDTO(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        "http://quepinto.pythonanywhere.com/media/"+resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getString(15),
                        resultSet.getString(16),
                        resultSet.getString(17)
                );
                eventos.add(eventoDTO);
            }
            ConnectDBHelper.desconectarBD();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventos;
    }
}
