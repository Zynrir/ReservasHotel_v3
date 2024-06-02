package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import static org.iesalandalus.programacion.utilidades.Entrada.cadena;


public class Consola {
    private static Doble doble;
    private static Suite suite;
    private static Triple triple;
    private Consola() {
    }

    public static void mostrarMenu() {
        System.out.println("---- Menu ----");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion.ordinal() + " - " + opcion.name());
        }
    }

    public static Opcion elegirOpcion() {
        System.out.print("Elija una opcion: ");
        int opcionElegida = Entrada.entero();
        return Opcion.values()[opcionElegida];
    }

    public static Huesped leerHuesped() {
        System.out.println("---- Introducir datos de Huesped ----");
        System.out.print("Introduzca el Nombre del Huesped: ");
        String nombre = cadena();
        System.out.print("Introduzca el DNI del Huesped: ");
        String dni = cadena();
        System.out.print("Introduzca el Correo del Huesped: ");
        String correo = cadena();
        System.out.print("Introduzca el Telefono del Huesped: ");
        String telefono = cadena();
        System.out.print("Introduzca el Fecha de nacimiento del Huesped: ");
        LocalDate fechaNacimiento = Consola.leerFecha();
        try {
            return new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static Habitacion leerHabitacion() {
        System.out.println("---- Introduce los datos de la habitacion ----");
        System.out.print("Introduzca la Planta de la habitacion: ");
        int planta = Entrada.entero();
        System.out.print("Introduzca el numero de la Puerta de la habitacion: ");
        int puerta = Entrada.entero();
        System.out.print("Introduzca el precio de la habitacion: ");
        double precio = Entrada.realDoble();
        System.out.println("Introduzca el tipo de habitacion: ");
        TipoHabitacion tipoHabitacion = leerTipoHabitacion();
        try {
            if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)){
                return new Simple(planta, puerta,precio);
            }else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)){
                System.out.println("Cuantas camas individuales quieres? (Escoje entre 0 y 2)");
                int camasIndividuales = Entrada.entero();
                System.out.println("Cuantas camas dobles quieres? (Escoje entre 0 y 1)");
                int camasDobles = Entrada.entero();
                return new Doble(planta, puerta, precio, camasIndividuales, camasDobles);
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                System.out.println("Cuantas camas individuales? (Entre 1 y 3)");
                int numCamasIndividuales = Entrada.entero();
                System.out.println("Cuantas camas dobles? (Entre 0 y 1)");
                int numCamasDobles = Entrada.entero();
                System.out.println("Cuantos baños quieres? (Minimo 1 y maximo 2)");
                int numBanos = Entrada.entero();
                return new Triple(planta, puerta, precio, numBanos, numCamasIndividuales, numCamasDobles);
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                String jacuzzi;
                do{
                    System.out.println("¿Desea Jacuzzi en la habitacion? Introduzca si o no");
                    jacuzzi=Entrada.cadena();
                }while(!jacuzzi.equalsIgnoreCase("si") && !jacuzzi.equalsIgnoreCase("no"));
                boolean tieneJacuzzi=false;
                if(jacuzzi.equalsIgnoreCase("si")) {
                    tieneJacuzzi=true;
                }
                return new Suite(planta, puerta, precio, 2, tieneJacuzzi);
            }else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static Habitacion leerHabitacionPorIdentificador() {
        System.out.print("Introduce la planta de la habitación: ");
        int planta = Entrada.entero();
        System.out.print("Introduce la puerta de la habitación: ");
        int puerta = Entrada.entero();
        try {
            TipoHabitacion tipoHabitacion = leerTipoHabitacion();
            if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
                return new Simple(planta, puerta, 50);
            }else if(tipoHabitacion.equals(TipoHabitacion.DOBLE)){
                return new Doble(planta, puerta, 50, 2, 0);
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                return new Triple(planta, puerta, 50, 2, 2,1);
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)){
                return new Suite(planta,puerta,50,2,true);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } return null;
    }

    public static TipoHabitacion leerTipoHabitacion() {
        System.out.println("Tipos de habitacion:");
        for (TipoHabitacion tipoHabitacion : EnumSet.allOf(TipoHabitacion.class)) {
            System.out.println(tipoHabitacion);
        }
        System.out.print("Elige un tipo de habitacion: ");
        int tipoElegido;
        do {
            tipoElegido = Entrada.entero();
        } while (tipoElegido < 0 || tipoElegido >= TipoHabitacion.values().length);
        return TipoHabitacion.values()[tipoElegido];
    }

    public static Regimen leerRegimen() {
        System.out.println("Tipo de regimen: ");
        for (Regimen regimen : Regimen.values()){
            System.out.println(regimen);
        }
        System.out.println("Elige un tipo de Regimen: ");
        int regimenElegido;
        do {
            regimenElegido = Entrada.entero();
        }while (regimenElegido < 0 || regimenElegido >= Regimen.values().length);
        return Regimen.values()[regimenElegido];

    }

    public static int leerNumeroPersonas() {
        int numeroPersonas;
        do {
            System.out.println("Ingresa el numero de personas: ");
            numeroPersonas = Entrada.entero();
            if (numeroPersonas <= 0) {
                System.out.println("El numero de personas debe ser mayor que 0. Por favor, intenta nuevamente.");
            }
        } while (numeroPersonas <= 0);
        return numeroPersonas;
    }

    public static Huesped getHuespedPorDni() {
        System.out.print("Introduzca el DNI del Huesped: ");
        String dni = cadena();
        try {
            return new Huesped(dni);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static LocalDate leerFecha() {
        String fecha = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.println("Formato dd/MM/yyyy");
            fecha = Entrada.cadena();
            if (fecha.matches("[0-3][0-9]/[0-1][0-9]/[1-2][0-9]{3}"))
                fechaValida = true;
        }
        DateTimeFormatter formato= DateTimeFormatter.ofPattern(Huesped.FORMATO_FECHA);
        LocalDate fechaFormato=LocalDate.parse(fecha, formato);
        return fechaFormato;
    }

    public static Huesped leerHuespedPorDni() {
        System.out.print("Introduce el DNI del huésped: ");
        String dni = Entrada.cadena();
        return new Huesped("nombre", dni, "correo@gmail.com", "623456789", LocalDate.of(2000,4,4));
    }
}

