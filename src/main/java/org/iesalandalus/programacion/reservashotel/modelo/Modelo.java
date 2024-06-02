package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.*;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;

public class Modelo {
    private final IHuespedes huespedes;
    private final IHabitaciones habitaciones;
    private final IReservas reservas;

    public Modelo(){
        huespedes = new Huespedes();
        habitaciones = new Habitaciones();
        reservas = new Reservas() {
            @Override
            public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
                return null;
            }
            @Override
            public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {

            }
            @Override
            public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
            }
        };

    }
    public void comenzar(){
    }

    public void terminar(){
        System.out.println("El modelo ha terminado.");
    }

    public void insertarHuesped(Huesped huesped) throws OperationNotSupportedException {
        huespedes.insertar(huesped);
    }

    public Huesped buscarHuesped(Huesped huesped){
        return huespedes.buscar(huesped);
    }

    public void borrarHuesped(Huesped huesped) throws OperationNotSupportedException {
        huespedes.borrar(huesped);
    }

    public List<Huesped> getHuespedes() {
        return huespedes.get();
    }

    public void insertarHabitacion(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.insertar(habitacion);
    }

    public Habitacion buscarHabitacion(Habitacion habitacion){
        return habitaciones.buscar(habitacion);
    }

    public void borrarHabitacion(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.borrar(habitacion);
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones.get();
    }

    public List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion) {
        return habitaciones.get(tipoHabitacion);
    }

    public void insertarReserva(Reserva reserva) throws OperationNotSupportedException {
        reservas.insertar(reserva);
    }

    public Reserva buscarReserva(Reserva reserva){
        return reservas.buscar(reserva);
    }

    public void borrarReserva(Reserva reserva) throws OperationNotSupportedException {
        reservas.borrar(reserva);
    }

    public List<Reserva> getReservas() {
        return reservas.get();
    }

    public List<Reserva> getReservas(Huesped huesped) {
        return reservas.getReservas(huesped);
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        return reservas.getReservas(tipoHabitacion);
    }

    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        return reservas.getReservasFuturas(habitacion);
    }


    //M?todos para gestionar checkIn y checkOut

    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {
        reservas.realizarCheckIn(reserva, fecha);
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
        reservas.realizarCheckOut(reserva, fecha);
    }
}

