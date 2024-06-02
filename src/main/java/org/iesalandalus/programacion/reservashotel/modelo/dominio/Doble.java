package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Doble extends Habitacion{
    private static final int NUM_MAXIMO_PERSONAS = 2;
    static final int MIN_NUM_CAMAS_INDIVIDUALES = 0;
    static final int MAX_NUM_CAMAS_INDIVIDUALES = 2;
    static final int MIN_NUM_CAMAS_DOBLES = 0;
    static final int MAX_NUM_CAMAS_DOBLES = 1;
    private int numCamasIndividuales;
    private int numCamasDobles;

    public Doble(int planta, int puerta, double precio, int numCamasIndividuales, int numCamasDobles) {
        super(planta, puerta, precio);
        setNumCamasIndividuales(numCamasIndividuales);
        setNumCamasDobles(numCamasDobles);
        validaNumCamas();
    }

    public Doble(Doble habitacionDoble) {
        super(habitacionDoble);
        setNumCamasIndividuales(habitacionDoble.getNumCamasIndividuales());
        setNumCamasDobles(habitacionDoble.getNumCamasDobles());
        validaNumCamas();
    }

    public int getNumCamasIndividuales() {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) {
        if(numCamasIndividuales < MIN_NUM_CAMAS_INDIVIDUALES || numCamasIndividuales > MAX_NUM_CAMAS_INDIVIDUALES){
            throw new IllegalArgumentException("ERROR: El n�mero de camas individuales de una habitaci�n triple no puede ser inferior a 1 ni mayor que 3");
        }
        this.numCamasIndividuales = numCamasIndividuales;
    }

    public int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        if(numCamasDobles < MIN_NUM_CAMAS_DOBLES || numCamasDobles > MAX_NUM_CAMAS_DOBLES){
            throw new IllegalArgumentException("ERROR: El n�mero de camas dobles de una habitaci�n triple no puede ser inferior a 0 ni mayor que 1");
        }
        this.numCamasDobles = numCamasDobles;
    }

    private void validaNumCamas(){
        if (getNumCamasIndividuales() < MIN_NUM_CAMAS_INDIVIDUALES || getNumCamasIndividuales() > MAX_NUM_CAMAS_INDIVIDUALES) {
            throw new IllegalArgumentException("ERROR: El numero de camas individuales de una habitacion doble no puede ser inferior a 0 ni mayor que 2");
        }
        if (getNumCamasDobles() < MIN_NUM_CAMAS_DOBLES || getNumCamasDobles() > MAX_NUM_CAMAS_DOBLES) {
            throw new IllegalArgumentException("ERROR: El numero de camas dobles de una habitacion doble no puede ser inferior a 0 ni mayor que 1");
        }
        if(getNumCamasIndividuales() != 2 && getNumCamasDobles() == 0 ){
            throw new IllegalArgumentException("ERROR: La distribucion de camas en una habitacion doble tiene que ser 2 camas individuales y 0 doble o 0 camas individuales y 1 doble");
        }
        if (getNumCamasIndividuales() != 0 && getNumCamasDobles() == 1){
            throw new IllegalArgumentException("ERROR: La distribucion de camas en una habitacion doble tiene que ser 2 camas individuales y 0 doble o 0 camas individuales y 1 doble");
        }
    }
    @Override
    public int getNUM_MAXIMO_PERSONAS() {
        return NUM_MAXIMO_PERSONAS;
    }

    public String toString() {
        return String.format("%s, habitacion doble, capacidad=%d personas, camas individuales=%d, camas dobles=%d",
                super.toString(), getNUM_MAXIMO_PERSONAS(), getNumCamasIndividuales(), getNumCamasDobles());
    }
}