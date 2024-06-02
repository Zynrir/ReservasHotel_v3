package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public abstract class Reservas implements IReservas {
    private final List<Reserva> coleccionReservas;
    public Reservas() {
        this.coleccionReservas = new ArrayList<>();
    }
    public List<Reserva> get() {
        return copiaProfundaReservas();
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if (buscar(reserva) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
        coleccionReservas.add(new Reserva(reserva));
    }

    public int getTamano() {
        return coleccionReservas.size();
    }

    public Reserva buscar(Reserva reserva) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.equals(reserva)) {
                return new Reserva(actual);
            }
        }
        return null;
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        if (!coleccionReservas.contains(reserva)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.equals(reserva)) {
                iterator.remove();
                return;
            }
        }
    }

    public List<Reserva> getReservas(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }
        List<Reserva> reservasHuesped = new ArrayList<>();
        for (Reserva reserva : coleccionReservas) {
            if (reserva.getHuesped().equals(huesped)) {
                reservasHuesped.add(reserva);
            }
        }
        return reservasHuesped;
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        List<Reserva> habitacionesTipo = new ArrayList<>();
        for(Reserva reserva : coleccionReservas) {
            if(tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
                if (reserva.getHabitacion() instanceof Simple) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
                if (reserva.getHabitacion() instanceof Doble) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                if (reserva.getHabitacion() instanceof Triple) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                if (reserva.getHabitacion() instanceof Suite) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            }
        }
        return new ArrayList<>(habitacionesTipo);
    }
    /*public ArrayList<Reserva> getReservas() {
        ArrayList<Reserva> reservasTipoHabitacion = new ArrayList<>();
        for (Reserva reserva : coleccionReservas) {
            if (reserva.getHabitacion().equals(tipoHabitacion)) {
                reservasTipoHabitacion.add(reserva);
            }
        }
        return reservasTipoHabitacion;
    }*/

    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }
        LocalDate fechaActual = LocalDate.now();
        List<Reserva> reservasFuturas = new ArrayList<>();
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.getHabitacion().equals(habitacion) && actual.getFechaInicioReserva().isAfter(fechaActual)) {
                reservasFuturas.add(new Reserva(actual));
            }
        }
        return reservasFuturas;
    }

    private List<Reserva> copiaProfundaReservas() {
        List<Reserva> copia = new ArrayList<>();
        for (Reserva reserva : coleccionReservas) {
            copia.add(new Reserva(reserva));
        }
        return copia;
    }

    public List<Reserva> getReservasAnulables(Huesped reservas) {
        List<Reserva> reservasAnulables = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        for (Reserva reserva : coleccionReservas) {
            if (reserva.getFechaInicioReserva().isAfter(fechaActual)) {
                reservasAnulables.add(reserva);
            }
        }
        return new ArrayList<>(reservasAnulables);
    }

    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }
        if(!coleccionReservas.contains(reserva)){
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }
        if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay())) {
            throw new IllegalArgumentException("ERROR: La fecha del checkIn no puede ser anterior a la reserva.");
        }
        // Utilizo un iterador para buscar la reserva en el ArrayList
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.equals(reserva)) {
                actual.setCheckIn(fecha);
                return;
            }
        }
    }
    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }
        if (reserva.getCheckIn() == null) {
            throw new NullPointerException("ERROR: No puedes hacer checkOut si el checkIn es nulo.");
        }
        if(!coleccionReservas.contains(reserva)) {
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }
        if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay()) || fecha.isBefore(reserva.getCheckIn())) {
            throw new IllegalArgumentException("ERROR: La fecha del checkOut no puede ser anterior a la de inicio de reserva o antes del checkIn.");
        }
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.equals(reserva)) {
                actual.setCheckOut(fecha);
                return;
            }
        }
    }
}

