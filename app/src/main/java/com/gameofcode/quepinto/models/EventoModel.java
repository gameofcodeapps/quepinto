package com.gameofcode.quepinto.models;

import android.text.format.DateFormat;
import android.util.Log;

import com.gameofcode.quepinto.DTO.ComentarioDTO;
import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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

    public List<EventoDTO> obtenerEventosFavoritosUsuarioLogeado(){
        UsuarioDTO usuarioLogeado = UsuarioModel.getInstance().getUsuarioLogeado();
        String sql = "SELECT evento.* " +
                "from home_evento as evento, " +
                "home_evento_favorito as favorito " +
                "WHERE " +
                "favorito.idUsuario = \"" + usuarioLogeado.getId() +"\"and " +
                "favorito.id_evento=evento.id";
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

    public boolean agregarComentario(EventoDTO pEvento, String pComentario){
        UsuarioModel instance = UsuarioModel.getInstance();
        try {
            ConnectDBHelper.establecerConexionBD();
            String sqlInsert="INSERT INTO home_evento_comentario (comentario,idUsuario,fecha,id_evento) values" +
                    "(\""+pComentario+"\",\""
                    +instance.getUsuarioLogeado().getId()+"\","
                    +" NOW(),\""
                    +pEvento.getId()+"\")";
            Log.i("SQL Insert",sqlInsert);
            int devuelveInsert = ConnectDBHelper.ejecutarSQLInsertUpdate(sqlInsert);
            Log.i("DevuelveInsert",String.valueOf(devuelveInsert));
            ConnectDBHelper.desconectarBD();
        }catch (Exception e){
            Log.i("error insert",e.getMessage());
            ConnectDBHelper.desconectarBD();
            return false;
        }
        return true;
    }

    //Agrega un favortio al usuario logeado
    public boolean agregarFavorito(EventoDTO pEvento){
        UsuarioModel instance = UsuarioModel.getInstance();
        try {
            ConnectDBHelper.establecerConexionBD();
            String sqlInsert="INSERT INTO home_usuario_favorito (idEvento,IdUsuario) values" +
                    "(\""+pEvento.getId()+"\",\""
                    +instance.getUsuarioLogeado().getId()+"\")";
            Log.i("SQL Insert",sqlInsert);
            int devuelveInsert = ConnectDBHelper.ejecutarSQLInsertUpdate(sqlInsert);
            Log.i("DevuelveInsert",String.valueOf(devuelveInsert));
            ConnectDBHelper.desconectarBD();
        }catch (Exception e){
            Log.i("error insert",e.getMessage());
            ConnectDBHelper.desconectarBD();
            return false;
        }
        return true;
    }

    public List<ComentarioDTO> obtenerComentariosDeEvento(EventoDTO pEvento){

        ResultSet resultSet = null;
        List<ComentarioDTO> comentarios = new ArrayList<ComentarioDTO>();
        String sql = "SELECT * from home_evento_comentario where id_evento =\""+pEvento.getId()+"\"";
        try {
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(sql);
            while (resultSet.next()) {
                ComentarioDTO comentarioDTO = new ComentarioDTO(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)
                );
                comentarios.add(comentarioDTO);
            }
            ConnectDBHelper.desconectarBD();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comentarios;
    }




}
