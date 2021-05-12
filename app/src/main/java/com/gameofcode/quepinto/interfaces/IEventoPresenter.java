package com.gameofcode.quepinto.interfaces;

import com.gameofcode.quepinto.DTO.EventoDTO;

public interface IEventoPresenter {

    public boolean agregarComentario(int pEventoID,String pComentario);
    public boolean borrarFavorito(EventoDTO pEvento);
    public boolean agregarFavorito(EventoDTO pEvento);

}
