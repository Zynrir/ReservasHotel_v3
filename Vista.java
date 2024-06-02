package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class Vista {
    private static Controlador controlador;

    public Vista() {
        Opcion.setVista(this);
    }

    public void setControlador(Controlador controlador) {
        if (controlador != null) {
            Vista.controlador = controlador;
        }
    }
    public void comenzar()  {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            opcion.ejecutar();
        } while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        System.out.println("¡Hasta luego!");
    }

    public void insertarHuesped(){
        try {
            Huesped nuevoHuesped = Consola.leerHuesped();
            controlador.insertar(nuevoHuesped);
            System.out.println("Huesped insertado correctamente.");
        }
        catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarHuesped(){
        try {
            Huesped huesped = Consola.getHuespedPorDni();
            Huesped huespedEncontrado = controlador.buscar(huesped);
            System.out.println(huespedEncontrado);
        }catch (IllegalArgumentException|NullPointerException e){
            System.out.println(e.getMessage());
        }
    }


    void borrarHuesped() {
        try {
            Huesped huespedABorrar = Consola.getHuespedPorDni();
            Controlador.borrar(huespedABorrar);
            System.out.println("Huesped borrado con exito.");
        }catch (OperationNotSupportedException e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void mostrarHuespedes() {
        List<Huesped> lista = controlador.getHuespedes();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay Huespedes almacenados.");
        } else {
            System.out.println("---- Listado de Huespedes ----");
            for (Huesped huesped : lista) {
                if (huesped != null) {
                    System.out.println(huesped);
                }
            }
        }
    }

    public void insertarHabitacion(){
        try {
            Habitacion nuevaHabitacion = Consola.leerHabitacion();
            controlador.insertar(nuevaHabitacion);
            System.out.println("Habitacion insertada correctamente.");
        } catch (IllegalArgumentException|OperationNotSupportedException|NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarHabitacion() {
        Habitacion habitacionBuscada = Consola.leerHabitacionPorIdentificador();
        try {
             habitacionBuscada = controlador.buscar(habitacionBuscada);
        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println("Error: "+ e.getMessage());
        }
        if (habitacionBuscada != null) {
            System.out.println("Habitacion encontrada: " + habitacionBuscada);
        } else {
            System.out.println("Habitacion no encontrada.");
        }
    }

    public void borrarHabitacion(){
        try {
            controlador.borrar(Consola.leerHabitacionPorIdentificador());
            System.out.println("Habitacion borrada correctamente.");
        } catch (IllegalArgumentException|NullPointerException|OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarHabitaciones() {
        List<Habitacion> listaHabitaciones = controlador.getHabitaciones();
        if (!listaHabitaciones.isEmpty()) {
            Iterator<Habitacion> iterator = listaHabitaciones.stream().sorted(Comparator.comparing(Habitacion::getPlanta).thenComparing(Habitacion::getPuerta)).iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } else {
            System.out.println("No hay habitaciones registradas.");
        }
    }

    void insertarReserva() {
        try {
            System.out.println("Introduce los datos de la reserva:");
            Huesped huesped = Consola.leerHuespedPorDni();
            Habitacion habitacion = Consola.leerHabitacionPorIdentificador();
            Regimen regimen = Consola.leerRegimen();
            System.out.println("Indica la fecha de inicio de la reserva:");
            LocalDate fechaInicioReserva = Consola.leerFecha();
            System.out.println("Indica la fecha de fin de la reserva:");
            LocalDate fechaFinReserva = Consola.leerFecha();
            int numeroPersonas = Consola.leerNumeroPersonas();
            Reserva nuevaReserva = new Reserva(huesped, habitacion, regimen, fechaInicioReserva, fechaFinReserva, numeroPersonas);
            Huesped huespedIntroducido = nuevaReserva.getHuesped();
            huespedIntroducido = controlador.buscar(huespedIntroducido);
            if (huespedIntroducido == null) {
                System.out.println("Huesped no encontrado.");
                return;
            }
            Habitacion habitacionIntroducida = nuevaReserva.getHabitacion();
            habitacionIntroducida = controlador.buscar(habitacionIntroducida);
            if (habitacionIntroducida == null) {
                System.out.println("Habitacion no encontrada.");
                return;
            }
            nuevaReserva = new Reserva(huespedIntroducido, habitacionIntroducida, nuevaReserva.getRegimen(), nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva(), nuevaReserva.getNumeroPersonas());
            TipoHabitacion habitacionTipo;
            if (nuevaReserva.getHabitacion() instanceof Simple) {
                habitacionTipo = TipoHabitacion.SIMPLE;
            } else if (nuevaReserva.getHabitacion() instanceof Doble) {
                habitacionTipo = TipoHabitacion.DOBLE;
            } else if (nuevaReserva.getHabitacion() instanceof Triple) {
                habitacionTipo = TipoHabitacion.TRIPLE;
            } else {
                habitacionTipo = TipoHabitacion.SUITE;
            }
            if (consultarDisponibilidad(habitacionTipo, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva()) == null) {
                controlador.insertar(nuevaReserva);
                System.out.println("Reserva insertada correctamente.");
            } else {
                System.out.println("No hay disponibilidad para las fechas indicadas.");
            }
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    public void mostrarReservasTipoHabitacion(){
        listarReservas(Consola.leerTipoHabitacion());
    }

    public void listarReservas(TipoHabitacion tipoHabitacion) {
        List<Reserva> reservasTipoHabitacion = controlador.getReservas(tipoHabitacion);
        if (!reservasTipoHabitacion.isEmpty()) {
            for (Reserva reserva : reservasTipoHabitacion) {
                System.out.println(reserva);
            }
        } else {
            System.out.println("No hay reservas para el tipo de habitacion " + tipoHabitacion);
        }
    }

    public void listarReservasPorDni(Huesped huesped) {
        List<Reserva> reservashuesped = controlador.getReservas(huesped);
        if (!reservashuesped.isEmpty()) {
            for (Reserva reserva : reservashuesped) {
                System.out.println(reserva);
            }
        } else {
            System.out.println("No hay reservas para este huesped: " + huesped);
        }
    }

    public void mostrarReservasHuesped(){
        listarReservasPorDni(Consola.getHuespedPorDni());
    }

    public List<Reserva> getReservasAnulables(List<Reserva> reservasAnular) {
        List<Reserva> misReservasAnulables= new ArrayList<>();
        for(Reserva misReservas : reservasAnular){
            if(misReservas.getFechaInicioReserva().isAfter(LocalDate.now())){
                misReservasAnulables.add(new Reserva(misReservas));
            }
        }
        return misReservasAnulables;
    }

    private void mostrarListaReservas(List<Reserva> lista, String mensaje) {
        if (!lista.isEmpty()) {
            System.out.println("---- Listado de " + mensaje + " ----");
            for (Reserva reserva : lista) {
                System.out.println(reserva);
            }
        } else {
            System.out.println("No hay " + mensaje + ".");
        }
    }
    public void anularReserva() {
        Huesped huesped = Consola.getHuespedPorDni();
        List<Reserva> reservasAnulables = controlador.getReservas(huesped);
        reservasAnulables = getReservasAnulables(reservasAnulables);
        if (reservasAnulables.isEmpty()) {
            System.out.println("No hay reservas para anular.");
        } else if (getNumElementosNoNulos(reservasAnulables) == 1) {
            System.out.println("¿Confirma la anulaci�n de la reserva? Escribe si o no" + reservasAnulables.get(0));
            if (Entrada.cadena().equalsIgnoreCase("si")) {
                try {
                    controlador.borrar(reservasAnulables.get(0));
                    System.out.println("Reserva anulada correctamente.");
                } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Anulación cancelada.");
            }
        } else {
            int contador = 0;
            Iterator<Reserva> iterator = reservasAnulables.iterator();
            while (iterator.hasNext()) {
                Reserva elemento = iterator.next();
                System.out.println(contador + " : " + elemento);
                contador++;
            }
            int indiceReserva;
            do {
                System.out.println("¿Que reserva desea anular?");
                indiceReserva = Entrada.entero();
            } while (indiceReserva < 0 || indiceReserva >= reservasAnulables.size());
            try {
                controlador.borrar(reservasAnulables.get(indiceReserva));
                System.out.println("Reserva anulada correctamente.");
            } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    void mostrarReservas() {
        List<Reserva> lista = controlador.getReservas();
        mostrarListaReservas(lista, "Todas las reservas");
    }

    public static int getNumElementosNoNulos(List<Reserva> reservas) {
        int contador = 0;
        for (Reserva elemento : reservas) {
            if (elemento != null) {
                contador++;
            }
        }
        return contador;
    }

    public void comprobarDisponibilidad() {
        System.out.println("Introduce el tipo de habitación: ");
        TipoHabitacion tipoHabitacionEscogida = Consola.leerTipoHabitacion();
        System.out.println("Introduce la fecha de inicio de reserva: ");
        LocalDate fechaInicioEscogida = Consola.leerFecha();
        System.out.println("Introduce la fecha de fin de reserva: ");
        LocalDate fechaFinEscogida = Consola.leerFecha();
        if (consultarDisponibilidad(tipoHabitacionEscogida,fechaInicioEscogida,fechaFinEscogida) == null){
            System.out.println("No hay disponibilidad.");
        }else {
            System.out.println("Hay disponibilidad.");
        }
    }


    private static Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        List<Habitacion> habitacionesTipoSolicitado = controlador.getHabitaciones(tipoHabitacion);
        if (habitacionesTipoSolicitado == null || habitacionesTipoSolicitado.isEmpty()){
            return habitacionDisponible;
        }
        for (Iterator<Habitacion> iterator = habitacionesTipoSolicitado.iterator(); iterator.hasNext() && !tipoHabitacionEncontrada; ) {
            Habitacion habitacion = iterator.next();
            if (habitacion == null){
                List<Reserva> reservasFuturas = new ArrayList<>(controlador.getReservasFuturas(habitacion));
                if (reservasFuturas.isEmpty()){
                    LocalDateTime checkIn = LocalDateTime.now();
                    if (habitacion instanceof Simple){
                        habitacionDisponible = new Simple((Simple) habitacion);
                    }else if (habitacion instanceof Doble){
                        habitacionDisponible = new Doble((Doble) habitacion);
                    } else if (habitacion instanceof Triple) {
                        habitacionDisponible = new Triple((Triple) habitacion);
                    } else if (habitacion instanceof Suite) {
                        habitacionDisponible = new Suite((Suite) habitacion);
                    }
                    tipoHabitacionEncontrada = true;
                }else {
                    reservasFuturas.sort(Comparator.comparing(Reserva::getFechaFinReserva).reversed());
                    if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva())){
                        if (habitacion instanceof Simple){
                            habitacionDisponible = new Simple((Simple) habitacion);
                        }else if (habitacion instanceof Doble){
                            habitacionDisponible = new Doble((Doble) habitacion);
                        } else if (habitacion instanceof Triple) {
                            habitacionDisponible = new Triple((Triple) habitacion);
                        } else if (habitacion instanceof Suite) {
                            habitacionDisponible = new Suite((Suite) habitacion);
                        }
                        tipoHabitacionEncontrada = true;
                    }
                    if (!tipoHabitacionEncontrada){
                        reservasFuturas.sort(Comparator.comparing(Reserva::getFechaFinReserva));
                        if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())){
                            if (habitacion instanceof Simple){
                                habitacionDisponible = new Simple((Simple) habitacion);
                            }else if (habitacion instanceof Doble){
                                habitacionDisponible = new Doble((Doble) habitacion);
                            } else if (habitacion instanceof Triple) {
                                habitacionDisponible = new Triple((Triple) habitacion);
                            } else if (habitacion instanceof Suite) {
                                habitacionDisponible = new Suite((Suite) habitacion);
                            }
                            tipoHabitacionEncontrada = true;
                        }
                    }
                    if (!tipoHabitacionEncontrada){
                        for (Iterator<Reserva> reservaIterator = reservasFuturas.iterator(); reservaIterator.hasNext() && !tipoHabitacionEncontrada; ) {
                            Reserva reservaAnterior = reservaIterator.next();
                            if (reservaIterator.hasNext()) {
                                Reserva reservaActual = reservaIterator.next();
                                if (fechaInicioReserva.isAfter(reservaAnterior.getFechaFinReserva()) && fechaFinReserva.isBefore(reservaActual.getFechaInicioReserva()))
                                {
                                    if (habitacion instanceof Simple) {
                                        habitacionDisponible = new Simple((Simple) habitacion);
                                    } else if (habitacion instanceof Doble) {
                                        habitacionDisponible = new Doble((Doble) habitacion);
                                    } else if (habitacion instanceof Triple) {
                                        habitacionDisponible = new Triple((Triple) habitacion);
                                    } else if (habitacion instanceof Suite) {
                                        habitacionDisponible = new Suite((Suite) habitacion);
                                    }
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return habitacionDisponible;
    }
    public void realizarCheckin() {
        Huesped huesped = Consola.getHuespedPorDni();
        System.out.println("Introduce la fecha (dd/MM/yyyy) y la hora (hh:mm:ss) del checkin:");
        LocalDateTime fechaCheckin = Consola.leerFecha().atStartOfDay();
        List<Reserva> reservasHuesped = controlador.getReservas(huesped);
        if (reservasHuesped.isEmpty()) {
            System.out.println("El huesped no tiene reservas.");
        } else if(getNumElementosNoNulos(reservasHuesped)==1){
            System.out.println("¿Quiere confirmar el checkIn de esta reserva? Escriba \"si\" o \"no\"");
            System.out.println(reservasHuesped.get(0));
            String confirmacion=Entrada.cadena();
            if(confirmacion.equalsIgnoreCase("si")){
                controlador.realizarCheckIn(reservasHuesped.get(0), fechaCheckin);
                System.out.println("CheckIn confirmado.");
            }
        }else{
            System.out.println("Reservas del huesped:");
            Iterator<Reserva> iterator = reservasHuesped.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                System.out.println(i + ": " + iterator.next());
                i++;
            }
            int indiceReserva;
            do {
                System.out.println("¿Que reserva desea hacer checkin?");
                indiceReserva = Entrada.entero();
            } while (indiceReserva < 0 || indiceReserva >= reservasHuesped.size());
            try {
                controlador.realizarCheckIn(reservasHuesped.get(indiceReserva), fechaCheckin);
                System.out.println("Checkin realizado correctamente.");
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void realizarCheckOut() {
        Huesped huesped = Consola.getHuespedPorDni();
        System.out.println("Introduce la fecha (dd/MM/yyyy) y la hora (hh:mm:ss) del checkOut:");
        LocalDateTime fechaCheckOut = Consola.leerFecha().atStartOfDay();
        List<Reserva> reservasHuesped = controlador.getReservas(huesped);
        if (reservasHuesped.isEmpty()) {
            System.out.println("El huesped no tiene reservas.");
        } else if (getNumElementosNoNulos(reservasHuesped) == 1) {
            System.out.println("¿Quiere confirmar el checkOut de esta reserva? Escriba \"si\" o \"no\"");
            System.out.println(reservasHuesped.get(0));
            String confirmacion = Entrada.cadena();
            if (confirmacion.equalsIgnoreCase("si")) {
                controlador.realizarCheckOut(reservasHuesped.get(0), fechaCheckOut);
                System.out.println("CheckOut confirmado.");
            }
        } else {
            System.out.println("Reservas del huusped:");
            Iterator<Reserva> iterator = reservasHuesped.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                System.out.println(i + ": " + iterator.next());
                i++;
            }
            int indiceReserva;
            do {
                System.out.println("¿Que reserva desea hacer checkOut?");
                indiceReserva = Entrada.entero();
            } while (indiceReserva < 0 || indiceReserva >= reservasHuesped.size());
            try {
                controlador.realizarCheckOut(reservasHuesped.get(indiceReserva), fechaCheckOut);
                System.out.println("CheckOut realizado correctamente.");
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
