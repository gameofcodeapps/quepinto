package com.gameofcode.quepinto.services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gameofcode.quepinto.DTO.ComentarioDTO;
import com.gameofcode.quepinto.MainActivityEvento;
import com.gameofcode.quepinto.Model;
import com.gameofcode.quepinto.R;
import com.gameofcode.quepinto.myviewholder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myadaptercomentario extends RecyclerView.Adapter<myviewholdercomentario>{
    String string;
    ArrayList<ComentarioDTO> data;
    Context context;
    public myadaptercomentario(ArrayList<ComentarioDTO> data, Context context)

    {
        this.data = data;
        this.context=context;

    }

    @NonNull
    @Override
    public myviewholdercomentario onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_main_comentario,parent,false);
        return new myviewholdercomentario(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholdercomentario holder, int position)
    {
        final ComentarioDTO temp=data.get(position);

        holder.t1.setText(temp.getUsuario());
          holder.t2.setText(temp.getComentario());
        holder.t3.setText(temp.getFecha());



        ///Alternativa a cargar imagen/////
        //holder.img.setImageBitmap(temp.getImgen());
        ////////////////////////////////////////////



    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

}
