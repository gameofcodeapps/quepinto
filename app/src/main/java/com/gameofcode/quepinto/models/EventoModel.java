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
        /*String sql = "SELECT evento.* " +
                "from home_evento as evento, " +
                "home_evento_favorito as favorito " +
                "WHERE " +
                "favorito.idUsuario = \"" + usuarioLogeado.getId() +"\"and " +
                "favorito.id_evento=evento.id";*/
        String sql = "SELECT home_evento.* " +
                "from home_evento, " +
                "home_usuario_favorito " +
                "WHERE " +
                "home_usuario_favorito.idUsuario = '41' and " +
                "home_usuario_favorito.idEvento=home_evento.id";
        return obtenerEventos(sql);

    }

    public List<EventoDTO> obtenerEventosFavoritosServicio(String pUsuario){
        String sql = "SELECT evento.* " +
                "from home_evento as evento, " +
                "home_usuario_favorito as favorito, " +
                "auth_user as usuario "+
                "WHERE " +
                "usuario.username= \""+pUsuario+"\" and "+
                "favorito.idUsuario = usuario.id and " +
                "favorito.idEvento=evento.id";
        //String sql = "SELECT home_evento.* FROM home_evento,home_usuario_favorito where home_evento.id=home_usuario_favorito.idEvento";
       // String sql = "SELECT * FROM home_evento";
        List<EventoDTO> eventoDTOS = obtenerEventos(sql);
        //Log.i("cantidad",sql);
        return eventoDTOS;

    }



    private List<EventoDTO> obtenerEventos(String pSQL){

        ResultSet resultSet = null;
        List<EventoDTO> eventos = new ArrayList<EventoDTO>();
        try {
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(pSQL);
            Log.i("SQL",pSQL);
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
            ConnectDBHelper.desconectarBD();
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

    public boolean agregarEvento(EventoDTO pEvento){
        String sqlUltimoID =  "select  id from home_evento order by id desc limit 1";
        ResultSet resultSet = null;
        int ultimoID=0;
        try {
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(sqlUltimoID);
            while(resultSet.next()){
                ultimoID = resultSet.getInt(1);
            }
            ultimoID = ultimoID+1;
            String sqlInsert="INSERT INTO home_evento values (" +
                    ultimoID+" ,"+
                    "\""+pEvento.getNombreEvento()+"\""+", " +
                    "\""+pEvento.getOrganizador()+"\""+", " +
                    "\""+pEvento.getCategoria()+"\""+", " +
                    "\""+pEvento.getDescripcion()+"\""+", " +
                    "\""+"images/sinfoto.jpg"+"\""+", " +
                    "\""+pEvento.getCiudad()+"\""+", " +
                    "\""+pEvento.getDepartamento()+"\""+", " +
                    "\""+pEvento.getPais()+"\""+", " +
                    "STR_TO_DATE('"+pEvento.getFechaInicio()+"','%d/%m/%Y')"+", " +
                    "STR_TO_DATE('"+pEvento.getFechaInicio()+"','%d/%m/%Y')"+", " +
                    "\""+"00:00"+"\""+", " +
                    "\""+pEvento.getHoraInicio()+"\""+", " +
                    "\""+pEvento.getLatitud()+"\""+", " +
                    "\""+pEvento.getDireccion()+"\""+", " +
                    "\""+pEvento.getLongitud()+"\""+", " +
                    "\""+pEvento.getUsuarioCreador()+"\""+")";
            Log.i("SQL Insert",sqlInsert);
            int devuelveInsert = ConnectDBHelper.ejecutarSQLInsertUpdate(sqlInsert);
            //Log.i("DevuelveInsert",String.valueOf(devuelveInsert));
            ConnectDBHelper.desconectarBD();
        }catch (Exception e){
            Log.i("error insert",e.getMessage());
            ConnectDBHelper.desconectarBD();
            return false;
        }

        return true;
    }

    public boolean actualizarDatosEvento(EventoDTO pEvento){
        UsuarioModel instance = UsuarioModel.getInstance();
        try {
            ConnectDBHelper.establecerConexionBD();
            String sqlInsert="UPDATE home_evento SET " +
                    "nameEvent=\""+pEvento.getNombreEvento()+"\", "+
                    "organizer=\""+pEvento.getOrganizador()+"\", "+
                    "category=\""+pEvento.getCategoria()+"\", "+
                    "description=\""+pEvento.getDescripcion()+"\", "+
                    "gallery=\""+pEvento.getImagenEvento()+"\", "+
                    "city=\""+pEvento.getCiudad()+"\", "+
                    "department=\""+pEvento.getDepartamento()+"\", "+
                    //"startDate="+"STR_TO_DATE('"+pEvento.getFechaInicio()+"','%Y-%m-%d')"+", "+
                    "startDate="+"STR_TO_DATE('"+pEvento.getFechaInicio()+"','%d/%m/%Y')"+", "+
                    "startTimeDate=\""+pEvento.getHoraInicio()+"\", "+
                    "lat=\""+pEvento.getLatitud()+"\", "+
                    "address=\""+pEvento.getDireccion()+"\", "+
                    "home_evento.long=\""+pEvento.getLongitud()+"\" "+
                    "WHERE " +
                    "id="+pEvento.getId();
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

}
