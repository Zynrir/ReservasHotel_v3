package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Suite extends Habitacion{
    private static final int NUM_MAXIMO_PERSONAS=4;
    static final int MIN_NUM_BANOS=2;
    static final int MAX_NUM_BANOS=2;
    private int numBanos;
    private boolean tieneJacuzzi;

    public Suite(int planta, int puerta, double precio, int numBanos, boolean tieneJacuzzi) {
        super(planta, puerta, precio);
        setNumBanos(numBanos);
        setTieneJacuzzi(tieneJacuzzi);
    }

    public Suite(Suite habitacionSuite) {
        super(habitacionSuite);
        setNumBanos(habitacionSuite.getNumBanos());
        setTieneJacuzzi(habitacionSuite.isTieneJacuzzi());
    }

    public int getNumBanos() {
        return numBanos;
    }

    public void setNumBanos(int numBanos) {
        if(numBanos<MIN_NUM_BANOS || numBanos >MAX_NUM_BANOS){
            throw new IllegalArgumentException("ERROR: El numero de baños no puede ser inferior a 2 ni superior a 2");
        }
        this.numBanos = numBanos;
    }

    public boolean isTieneJacuzzi() {
        return tieneJacuzzi;
    }

    public void setTieneJacuzzi(boolean tieneJacuzzi) {
        this.tieneJacuzzi = tieneJacuzzi;
    }

    @Override
    public int getNUM_MAXIMO_PERSONAS() {
        return NUM_MAXIMO_PERSONAS;
    }

    public String toString() {
        return String.format("%s, habitacion suite, capacidad=%d personas, banos=%d, %s",
                super.toString(), getNUM_MAXIMO_PERSONAS(), numBanos, tieneJacuzzi ? "con Jacuzzi" : "sin Jacuzzi");
    }
}
