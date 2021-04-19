package com.gameofcode.quepinto;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myviewholder extends RecyclerView.ViewHolder
{
    ImageView img;
    TextView t1,t2,t3,t4,t5;
    public myviewholder(@NonNull View itemView)
    {
        super(itemView);
        img=(ImageView)itemView.findViewById(R.id.imgEvento);
        t1=(TextView)itemView.findViewById(R.id.TxtNomEvento);
        t3=(TextView)itemView.findViewById(R.id.txtfchEve);

      //  t5=(TextView)itemView.findViewById(R.id.txtMapa);
    }
}