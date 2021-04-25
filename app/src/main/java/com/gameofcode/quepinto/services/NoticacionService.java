package com.gameofcode.quepinto.services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.R;
import com.gameofcode.quepinto.broadcast_reciver.BootReceiver;
import com.gameofcode.quepinto.helpers.PreferenciasSistema;
import com.gameofcode.quepinto.models.EventoModel;

import java.util.List;

public class NoticacionService extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {
        //Log.d(this.getClass().getSimpleName(),"onStartJob");
        String usuario = PreferenciasSistema.leerPreferencia(getApplicationContext(), "usuario");

        Handler getHandler = new Handler(getMainLooper()) {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i("Manejador",usuario );
                createNotificationChannel();
                List<EventoDTO> listaFavoritos = (List<EventoDTO>) msg.obj;
                if(listaFavoritos.size()>0){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"QuePinto")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Tenes "+String.valueOf(listaFavoritos.size()) +" eventos favoritos proximos")
                            .setContentText("Hace click para ver tus favoritos")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(100, builder.build());}
                super.handleMessage(msg);
            }
        };
        new Thread(){
            public void run(){
                Message mensaje = new Message();
                List<EventoDTO> eventoDTOS = EventoModel.getInstance().obtenerEventosFavoritosServicio(usuario);
                Log.i("cantidad de eventos:",String.valueOf(eventoDTOS.size()) );
                mensaje.obj=eventoDTOS;
                mensaje.what = 1;
                getHandler.sendMessage(mensaje);


            }
        }.start();




        //Toast.makeText(getApplicationContext(),"Soy un servicio re piolaaa "+usuario, Toast.LENGTH_SHORT).show();



        jobFinished(params, false);
        Log.i("Dentro del servcice","en el hilo");
        //Toast.makeText(getApplicationContext(),"re loco el servicio",Toast.LENGTH_LONG).show();
        BootReceiver.scheduleJob(getApplicationContext());

        return true;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "QuePinto";
            String description = "Canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("QuePinto", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
        }
    }
    /*
    private  class ManejadorUI extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.i("Manejador","dentro" );
            createNotificationChannel();
            List<EventoDTO> listaFavoritos = (List<EventoDTO>) msg.obj;
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"QuePinto")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Tenes"+String.valueOf(listaFavoritos.size()) +" eventos favoritos proximos")
                    .setContentText("Hace click para ver tus favoritos")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(100, builder.build());
            super.handleMessage(msg);
        }
    }*/
}
