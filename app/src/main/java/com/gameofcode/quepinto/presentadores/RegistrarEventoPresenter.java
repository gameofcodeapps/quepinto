package com.gameofcode.quepinto.presentadores;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.interfaces.IRegistrarEventoPresenter;
import com.gameofcode.quepinto.models.EventoModel;

public class RegistrarEventoPresenter implements IRegistrarEventoPresenter {

    @Override
    public int registrarEvento(EventoDTO pEvento) {

        EventoModel eventoModel = EventoModel.getInstance();

        return 0;
    }
}
