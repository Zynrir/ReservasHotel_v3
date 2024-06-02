package org.iesalandalus.programacion.reservashotel.controlador;

import org.iesalandalus.programacion.reservashotel.modelo.*;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.vista.*;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;

public class Controlador {

    private static Modelo modelo;
    private Vista vista;


    //M?TODOS CONSTRUCTOR, COMENZAR Y TERMINAR

    public Controlador(Modelo modelo, Vista vista){

        if (modelo == null || vista == null) {
            throw new IllegalArgumentException("ERROR: El modelo y la vista no pueden ser nulos.");
        }
        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this); //Paso como par?metro el controlador

    }

    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar(){
        modelo.terminar();
        vista.terminar();
    }


    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        modelo.insertarHuesped(huesped);


    }

    public Huesped buscar (Huesped huesped){
        return modelo.buscarHuesped(huesped);
    }

    public static void borrar(Huesped huesped) throws OperationNotSupportedException {
        modelo.borrarHuesped(huesped);

    }

    public List<Huesped> getHuespedes() {
        return modelo.getHuespedes();
    }


    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        modelo.insertarHabitacion(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion){
        return modelo.buscarHabitacion(habitacion);
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        modelo.borrarHabitacion(habitacion);
    }

    public List<Habitacion> getHabitaciones() {
        return modelo.getHabitaciones();
    }

    public List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion) {
        return modelo.getHabitaciones(tipoHabitacion);
    }
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        modelo.insertarReserva(reserva);
    }

    public void borrar (Reserva reserva) throws OperationNotSupportedException {
        modelo.borrarReserva(reserva);
    }
    public Reserva buscar(Reserva reserva){
        return modelo.buscarReserva(reserva);

    }
    public List<Reserva> getReservas() {
        return modelo.getReservas();
    }
    public List<Reserva> getReservas(Huesped huesped) {
        return modelo.getReservas();
    }
    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        return modelo.getReservas();
    }
    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        return modelo.getReservasFuturas(habitacion);
    }
    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha)  {
        modelo.realizarCheckIn(reserva, fecha);

    }
    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha)  {
        modelo.realizarCheckOut(reserva, fecha);

    }
}