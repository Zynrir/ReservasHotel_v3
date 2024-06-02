package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Simple extends Habitacion{

    private static final int NUM_MAXIMO_PERSONAS=1;

    public Simple(int planta, int puerta, double precio) {
        super(planta, puerta, precio);
    }

    public Simple(Simple habitacionSimple) {
        super(habitacionSimple);
    }

    @Override
    public int getNUM_MAXIMO_PERSONAS() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return String.format("%s, habitacion simple, capacidad=%d personas",
                super.toString(), getNUM_MAXIMO_PERSONAS());
    }
}