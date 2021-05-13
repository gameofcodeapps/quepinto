package com.gameofcode.quepinto.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;

import com.gameofcode.quepinto.DTO.ComentarioDTO;
import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.R;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        return obtenerEventos(sql,true);
    }

    public List<EventoDTO> obtenerEventosCreadosPorUsuarioLogeado(){
        String sql = "SELECT * from home_evento where user_owner=\""+UsuarioModel.getInstance().getUsuarioLogeado().getUsername()+"\"";
        return obtenerEventos(sql,true);
    }

    public List<EventoDTO> obtenerEventosFavoritosUsuarioLogeado(){
        UsuarioDTO usuarioLogeado = UsuarioModel.getInstance().getUsuarioLogeado();
        /*String sql = "SELECT evento.* " +
                "from home_evento as evento, " +
                "home_evento_favorito as favorito " +
                "WHERE " +
                "favorito.idUsuario = \"" + usuarioLogeado.getId() +"\"and " +
                "favorito.id_evento=evento.id";*/

        return usuarioLogeado.getEventoFavoritos();

    }
    public List<EventoDTO> obtenerEventosFavoritosUsuario(){
        UsuarioDTO usuarioLogeado = UsuarioModel.getInstance().getUsuarioLogeado();
        String sql = "SELECT evento.* " +
                "from home_evento as evento, " +
                "home_usuario_favorito as favorito " +
                "WHERE " +
                "favorito.idUsuario = " + usuarioLogeado.getId() +" and " +
                "favorito.idEvento=evento.id";
        Log.i("SQL favoritos",sql);
        return obtenerEventos(sql,false);
    }

    public boolean borrarFavorito(EventoDTO pEvento){
        UsuarioModel instance = UsuarioModel.getInstance();
        String SQL = "delete from home_usuario_favorito where idEvento = "+pEvento.getId() +
                " and idUsuario= " + instance.getUsuarioLogeado().getId();
        try {
            ConnectDBHelper.establecerConexionBD();
            ConnectDBHelper.ejecutarSQLInsertUpdate(SQL);
            instance.getUsuarioLogeado().setEventoFavoritos(this.obtenerEventosFavoritosUsuario());
            ConnectDBHelper.desconectarBD();
        } catch (Exception e) {
            Log.i("error insert",e.getMessage());
            ConnectDBHelper.desconectarBD();
            return false;
        }
        return true;
    }



    public List<EventoDTO> obtenerEventosPorCategoriaONombre(String pBusqueda){
        String sql = "select *  " +
                "from home_evento " +
                "WHERE " +
                "(lower(category) like trim(lower(\"%"+pBusqueda+"%\"))) or " +
                "(trim(lower(nameEvent)) like trim(lower(\"%"+pBusqueda+"%\")))";
        Log.i("SQL",sql);
        return obtenerEventos(sql,true);

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
        List<EventoDTO> eventoDTOS = obtenerEventos(sql,true);
        //Log.i("cantidad",sql);
        return eventoDTOS;

    }



    private List<EventoDTO> obtenerEventos(String pSQL, boolean pConectarse){

        ResultSet resultSet = null;
        List<EventoDTO> eventos = new ArrayList<EventoDTO>();
        try {
            if(pConectarse) {
                ConnectDBHelper.establecerConexionBD();
            }
            resultSet = ConnectDBHelper.ejecutarSQL(pSQL);
            Log.i("SQL",pSQL);
            while (resultSet.next()) {

                EventoDTO eventoDTO = new EventoDTO(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        "https://quepinto.pythonanywhere.com/media/"+resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10) + " " +resultSet.getString(13).substring(0,5),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getString(15),
                        resultSet.getString(16),
                        resultSet.getString(17)
                );
                ///Alternativa a cargar imagen/////
               /* URL url = new URL(eventoDTO.getImagenEvento());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                eventoDTO.setImagenEventoBMP(bmp);*/
                ////////////////////////////////////
                eventos.add(eventoDTO);
                //Log.i("fecha",resultSet.getString(18));
            }
            if(pConectarse){
                ConnectDBHelper.desconectarBD();
            }
        } catch (Exception e) {
            if(pConectarse){
                ConnectDBHelper.desconectarBD();
            }
            e.printStackTrace();
        }
        return eventos;
    }

    public boolean agregarComentario(int pEventoID, String pComentario){
        UsuarioModel instance = UsuarioModel.getInstance();
        try {
            ConnectDBHelper.establecerConexionBD();
            String sqlInsert="INSERT INTO home_evento_comentario (comentario,idUsuario,fecha,id_evento) values" +
                    "(\""+pComentario+"\",\""
                    +instance.getUsuarioLogeado().getId()+"\","
                    +" NOW(),\""
                    +pEventoID+"\")";
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
            String sqlInsert="INSERT INTO home_usuario_favorito (idEvento,idUsuario) values " +
                    "("+pEvento.getId()+","
                    +instance.getUsuarioLogeado().getId()+")";
            Log.i("SQL Insert",sqlInsert);
            int devuelveInsert = ConnectDBHelper.ejecutarSQLInsertUpdate(sqlInsert);
            Log.i("DevuelveInsert",String.valueOf(devuelveInsert));
            instance.getUsuarioLogeado().setEventoFavoritos(this.obtenerEventosFavoritosUsuario());
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
        String sql = "SELECT home_evento_comentario.*,auth_user.username from home_evento_comentario,auth_user where" +
                " id_evento ="+pEvento.getId()+" and " +
                "auth_user.id=home_evento_comentario.idUsuario";
        Log.i("SQL",sql);
        try {
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(sql);
            while (resultSet.next()) {
                ComentarioDTO comentarioDTO = new ComentarioDTO(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getString(6)
                );
                comentarios.add(comentarioDTO);
            }
            ConnectDBHelper.desconectarBD();
        } catch (Exception e) {
            ConnectDBHelper.desconectarBD();
            e.printStackTrace();
        }
        return comentarios;
    }

    public boolean agregarEvento(EventoDTO pEvento){
        String sqlUltimoID =  "select  id from home_evento order by id desc limit 1";
        ResultSet resultSet = null;
        String nombreFoto="";
        int ultimoID=0;
        try {
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(sqlUltimoID);
            while(resultSet.next()){
                ultimoID = resultSet.getInt(1);
            }
            ultimoID = ultimoID+1;
            if (pEvento.getImagenEventoBMP() != null){
                nombreFoto=String.valueOf(ultimoID)+"_"+pEvento.getNombreEvento()+".jpeg";
                nombreFoto.replaceAll("\\s+","_");
            }else{
                nombreFoto="sinfoto.jpg";
            }

            String sqlInsert="INSERT INTO home_evento values (" +
                    ultimoID+" ,"+
                    "\""+pEvento.getNombreEvento()+"\""+", " +
                    "\""+pEvento.getOrganizador()+"\""+", " +
                    "\""+pEvento.getCategoria()+"\""+", " +
                    "\""+pEvento.getDescripcion()+"\""+", " +
                    "\""+"images/"+nombreFoto+"\""+", " +
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
            if (pEvento.getImagenEventoBMP() != null){
                subirFotoServidor(pEvento.getImagenEventoBMP(), nombreFoto);
            }
        }catch (Exception e){
            Log.i("error insert",e.getMessage());
            ConnectDBHelper.desconectarBD();
            return false;
        }

        return true;
    }

    public boolean actualizarDatosEvento(EventoDTO pEvento){
        UsuarioModel instance = UsuarioModel.getInstance();
        String sqlInsert="";
        try {
            ConnectDBHelper.establecerConexionBD();
            if(pEvento.getImagenEventoBMP()!= null){
                String nombreFoto=pEvento.getId()+"_"+String.valueOf(Math.random()*100000).substring(0,5)+"_"+pEvento.getNombreEvento()+".jpeg";
                Log.i("nombre_foto",nombreFoto);
                nombreFoto.replaceAll(" ","_");
                sqlInsert="UPDATE home_evento SET " +
                        "nameEvent=\""+pEvento.getNombreEvento()+"\", "+
                        "organizer=\""+pEvento.getOrganizador()+"\", "+
                        "category=\""+pEvento.getCategoria()+"\", "+
                        "description=\""+pEvento.getDescripcion()+"\", "+
                        "gallery=\"images/"+nombreFoto+"\", "+
                        "city=\""+pEvento.getCiudad()+"\", "+
                        "department=\""+pEvento.getDepartamento()+"\", "+
                        "startDate="+"STR_TO_DATE('"+pEvento.getFechaInicio()+"','%Y-%m-%d')"+", "+
                        //"startDate="+"STR_TO_DATE('"+pEvento.getFechaInicio()+"','%d/%m/%Y')"+", "+
                        "startTimeDate=\""+pEvento.getHoraInicio()+"\", "+
                        "lat=\""+pEvento.getLatitud()+"\", "+
                        "address=\""+pEvento.getDireccion()+"\", "+
                        "home_evento.long=\""+pEvento.getLongitud()+"\" "+
                        "WHERE " +
                        "id="+pEvento.getId();
                Log.i("nombreFoto",nombreFoto);
                subirFotoServidor(pEvento.getImagenEventoBMP(), nombreFoto);
            }else{
                sqlInsert="UPDATE home_evento SET " +
                        "nameEvent=\""+pEvento.getNombreEvento()+"\", "+
                        "organizer=\""+pEvento.getOrganizador()+"\", "+
                        "category=\""+pEvento.getCategoria()+"\", "+
                        "description=\""+pEvento.getDescripcion()+"\", "+
                        //"gallery=\""+nombreFoto+"\", "+
                        "city=\""+pEvento.getCiudad()+"\", "+
                        "department=\""+pEvento.getDepartamento()+"\", "+
                        "startDate="+"STR_TO_DATE('"+pEvento.getFechaInicio()+"','%Y-%m-%d')"+", "+
                        //"startDate="+"STR_TO_DATE('"+pEvento.getFechaInicio()+"','%d/%m/%Y')"+", "+
                        "startTimeDate=\""+pEvento.getHoraInicio()+"\", "+
                        "lat=\""+pEvento.getLatitud()+"\", "+
                        "address=\""+pEvento.getDireccion()+"\", "+
                        "home_evento.long=\""+pEvento.getLongitud()+"\" "+
                        "WHERE " +
                        "id="+pEvento.getId();
            }

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
    private String codificarImagenBASE64(Bitmap pBitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        String resultado = "data:image/jpeg;base64,"+imageString;
        return resultado;
    }

    private boolean subirFotoServidor(Bitmap pBitmap, String pNombre) throws  Exception{
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("imagen", codificarImagenBASE64(pBitmap))
                .addFormDataPart("nombre", pNombre)
                .build();

        Request request = new Request.Builder()
                .url("https://quepinto.pythonanywhere.com/home/upload_image")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            return false;
        };
        /*Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }*/
        //Log.i("llegue ","makepost");
        Log.i("resultado POST",response.body().string());
        //System.out.println(response.body().string());
        return true;
    }



}
