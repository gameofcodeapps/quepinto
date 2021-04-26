package com.gameofcode.quepinto.presentadores;

import com.gameofcode.quepinto.interfaces.IEventoPresenter;
import com.gameofcode.quepinto.models.EventoModel;

public class EventoPresenter implements IEventoPresenter {

    @Override
    public boolean agregarComentario(int pEventoID,String pComentario) {
        return EventoModel.getInstance().agregarComentario(pEventoID,pComentario);

    }
}
