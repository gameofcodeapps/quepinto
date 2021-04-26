package com.gameofcode.quepinto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myviewholder>
{
    String string;
    ArrayList<Model> data;
    Context context;
    public myadapter(ArrayList<Model> data, Context context)

    {
        this.data = data;
        this.context=context;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_main_listado_evento,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, int position)
    {
        final Model temp=data.get(position);

        holder.t1.setText(temp.getHeader());
     //   holder.t2.setText(temp.getDesc());
        holder.t3.setText(temp.getFecha());
     //   holder.t4.setText(temp.getOrganizador());
/*        holder.t5.setText(temp.getTxtmapa());*/

   //     holder.img.setImageResource(temp.getImgname());


        String string = temp.getUrlimagen();
        //string=string.replace("http:/", "https:/");
        Picasso.with(holder.img.getContext()).load(string).into(holder.img);


        ///Alternativa a cargar imagen/////
        //holder.img.setImageBitmap(temp.getImgen());
        ////////////////////////////////////////////

        holder.img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,MainActivityEvento.class);
                intent.putExtra("imagename",temp.getImgname());
                intent.putExtra("header",temp.getHeader());
                intent.putExtra("desc",temp.getDesc());
                intent.putExtra("fecha",temp.getFecha());
                intent.putExtra("organizador",temp.getOrganizador());
                intent.putExtra("mapa",temp.getTxtmapa());
                intent.putExtra("imagen",temp.getUrlimagen());
                intent.putExtra("esFavorito",temp.isEsFavorito());
                //Agregado para compartir en la web
                intent.putExtra("idEvento",temp.getId());
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
