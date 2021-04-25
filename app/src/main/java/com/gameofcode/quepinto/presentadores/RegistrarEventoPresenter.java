package com.gameofcode.quepinto.presentadores;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.interfaces.IRegistrarEventoPresenter;
import com.gameofcode.quepinto.models.EventoModel;

public class RegistrarEventoPresenter implements IRegistrarEventoPresenter {

    //Si retorna 0 esta todo bien, si retorna 1 hay error

    @Override
    public int registrarEvento(EventoDTO pEvento) {

        EventoModel eventoModel = EventoModel.getInstance();
        if(eventoModel.agregarEvento(pEvento)){
            return 0;
        }
        return 1;
    }
}
