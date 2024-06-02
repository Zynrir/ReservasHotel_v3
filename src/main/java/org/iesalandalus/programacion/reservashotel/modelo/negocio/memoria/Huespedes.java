package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Huespedes implements IHuespedes {
    private final List<Huesped> coleccionHuespedes;

    public Huespedes() {
        this.coleccionHuespedes = new ArrayList<>();
    }

    @Override
    public List<Huesped> get() {
       return copiaProfundaHuespedes();
    }

    private List<Huesped> copiaProfundaHuespedes() {
        List<Huesped> copia = new ArrayList<>();
        Iterator<Huesped> huespedIt = coleccionHuespedes.iterator();
        while (huespedIt.hasNext()){
            copia.add(new Huesped(huespedIt.next()));
        }
        return copia;
    }

    public int getTamano() {
        return coleccionHuespedes.size();
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        if (buscar(huesped) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
        coleccionHuespedes.add(new Huesped(huesped));
    }
    public Huesped buscar(Huesped huesped) {
        for (Huesped h : coleccionHuespedes) {
            if (h != null && h.equals(huesped)) {
                return h;
            }
        }
        return null;
    }
    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }
        if(!coleccionHuespedes.contains(huesped)){
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
        Iterator<Huesped> iterator = coleccionHuespedes.iterator();
        while (iterator.hasNext()) {
            Huesped actual = iterator.next();
            if (actual.equals(huesped)) {
                iterator.remove();
                return;
            }
        }

    }
}
