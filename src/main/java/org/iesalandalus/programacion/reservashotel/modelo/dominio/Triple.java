package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Triple extends Habitacion{
    private static final int NUM_MAXIMO_PERSONAS = 3;
    static final int MIN_NUM_BANOS = 1;
    static final int MAX_NUM_BANOS = 2;
    static final int MIN_NUM_CAMAS_INDIVIDUALES = 1;
    static final int MAX_NUM_CAMAS_INDIVIDUALES = 3;
    static final int MIN_NUM_CAMAS_DOBLES = 0;
    static final int MAX_NUM_CAMAS_DOBLES = 1;
    private int numBanos;
    private int numCamasIndividuales;
    private int numCamasDobles;


    public Triple(int planta, int puerta, double precio, int numBanos, int numCamasIndividuales, int numCamasDobles) {
        super(planta, puerta, precio);
        setNumBanos(numBanos);
       setNumCamasIndividuales(numCamasIndividuales);
        setNumCamasDobles(numCamasDobles);
        validaNumCamas();
    }

    public Triple(Triple habitacionTriple) {
        super(habitacionTriple);
        setNumBanos(habitacionTriple.getNumBanos());
        setNumCamasIndividuales(habitacionTriple.getNumCamasIndividuales());
        setNumCamasDobles(habitacionTriple.getNumCamasDobles());
        validaNumCamas();
    }
    public int getNumBanos() {
        return numBanos;
    }
    public void setNumBanos(int numBanos) {
        if(numBanos<MIN_NUM_BANOS || numBanos>MAX_NUM_BANOS){
            throw new IllegalArgumentException("ERROR: El n?mero de ba?os no puede ser inferior a 1 ni superior a 2");
        }
        this.numBanos = numBanos;
    }
    public int getNumCamasIndividuales() {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) {
        if(numCamasIndividuales < MIN_NUM_CAMAS_INDIVIDUALES || numCamasIndividuales > MAX_NUM_CAMAS_INDIVIDUALES){
            throw new IllegalArgumentException("ERROR: El número de camas individuales de una habitación triple no puede ser inferior a 1 ni mayor que 3");
        }
        this.numCamasIndividuales = numCamasIndividuales;
    }

    public int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        if(numCamasDobles < MIN_NUM_CAMAS_DOBLES ||  numCamasDobles > MAX_NUM_CAMAS_DOBLES){
            throw new IllegalArgumentException("ERROR: El número de camas dobles de una habitación triple no puede ser inferior a 0 ni mayor que 1");
        }
        this.numCamasDobles = numCamasDobles;
    }
    private void validaNumCamas(){
        if (getNumCamasIndividuales() < MIN_NUM_CAMAS_INDIVIDUALES || getNumCamasIndividuales() > MAX_NUM_CAMAS_INDIVIDUALES) {
            throw new IllegalArgumentException("ERROR: El numero de camas individuales de una habitaci?n triple no puede ser inferior a 1 ni mayor que 3");
        }
        if (getNumCamasDobles() < MIN_NUM_CAMAS_DOBLES || getNumCamasDobles() > MAX_NUM_CAMAS_DOBLES) {
            throw new IllegalArgumentException("ERROR: El numero de camas dobles de una habitaci?n triple no puede ser inferior a 0 ni mayor que 1");
        }
        if(getNumCamasIndividuales() !=3 && getNumCamasDobles()==0){
            throw new IllegalArgumentException("ERROR: La distribucion de camas en una habitaci?n triple tiene que ser 3 camas individuales y 0 doble o 1 cama individual y 1 doble");
        }
        if(getNumCamasIndividuales() !=1 && getNumCamasDobles() == 1){
            throw new IllegalArgumentException("ERROR: La distribucion de camas en una habitaci?n triple tiene que ser 3 camas individuales y 0 doble o 1 cama individual y 1 doble");
        }
    }
    @Override
    public int getNUM_MAXIMO_PERSONAS() {
        return NUM_MAXIMO_PERSONAS;
    }

    public String toString() {
        return String.format("%s, habitacion triple, capacidad=%d personas, baños=%d, camas individuales=%d, camas dobles=%d",
                super.toString(), getNUM_MAXIMO_PERSONAS(), getNumBanos(), getNumCamasIndividuales(), getNumCamasDobles());
    }
}
