package com.gameofcode.quepinto.presentadores;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.interfaces.IEventoPresenter;
import com.gameofcode.quepinto.models.EventoModel;
import com.gameofcode.quepinto.models.UsuarioModel;

public class EventoPresenter implements IEventoPresenter {

    @Override
    public boolean agregarComentario(int pEventoID,String pComentario) {
        return EventoModel.getInstance().agregarComentario(pEventoID,pComentario);

    }

    @Override
    public boolean borrarFavorito(EventoDTO pEvento){
        EventoModel instance = EventoModel.getInstance();
        return instance.borrarFavorito(pEvento);
    }

    @Override
    public boolean agregarFavorito(EventoDTO pEvento){
        EventoModel instance = EventoModel.getInstance();
        return  instance.agregarFavorito(pEvento);

    }

}
