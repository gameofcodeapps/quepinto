package com.gameofcode.quepinto.presentadores;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.interfaces.IModificarEventoPresenter;
import com.gameofcode.quepinto.models.EventoModel;

public class ModificarEventoPresenter implements IModificarEventoPresenter{

    public boolean modificarEvento(EventoDTO pEvento){
        return EventoModel.getInstance().actualizarDatosEvento(pEvento);
    }
}
