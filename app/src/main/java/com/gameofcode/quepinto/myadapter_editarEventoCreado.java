package com.gameofcode.quepinto;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class myadapter_editarEventoCreado extends RecyclerView.Adapter<myviewholder>
{
    String string;
    List<EventoDTO> data;
    Context context;
    public myadapter_editarEventoCreado(List<EventoDTO> eventoData, Context context)

    {
        this.data = eventoData;
        this.context=context;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_main_listado_evento_creado,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, int position)
    {
        final EventoDTO temp=data.get(position);

        holder.t1.setText(temp.getNombreEvento());
     //   holder.t2.setText(temp.getDesc());
        holder.t3.setText(temp.getFechaInicio());
     //   holder.t4.setText(temp.getOrganizador());
/*        holder.t5.setText(temp.getTxtmapa());*/

   //     holder.img.setImageResource(temp.getImgname());


        String string = temp.getImagenEvento();
        //string=string.replace("http:/", "https:/");
        Picasso.with(holder.img.getContext()).load(string).into(holder.img);


        ///Alternativa a cargar imagen/////
        //holder.img.setImageBitmap(temp.getImgen());
        ////////////////////////////////////////////

        holder.img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,MainActivityModificarEvento.class);
                intent.putExtra("evento",  temp);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });        /*    holder.t2.setText(temp.getDesc());
      holder.t3.setText(temp.getFecha());



       */

    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }
}
