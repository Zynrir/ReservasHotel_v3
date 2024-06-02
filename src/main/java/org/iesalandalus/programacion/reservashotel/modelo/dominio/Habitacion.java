package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;

public abstract class Habitacion {
    public final double MIN_PRECIO_HABITACION = 40.0;
    public final double MAX_PRECIO_HABITACION = 150.0;
    public final int MIN_NUMERO_PUERTA = 1;
    public final int MAX_NUMERO_PUERTA = 15;
    public final int MIN_NUMERO_PLANTA = 1;
    public final int MAX_NUMERO_PLANTA = 3;
    private String identificador;
    private int planta;
    private int puerta;
    private double precio;

    public Habitacion(int planta, int puerta, double precio) {
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador();
    }
    public Habitacion(Habitacion habitacion){
        if(habitacion==null){
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }
        this.planta = habitacion.planta;
        this.puerta = habitacion.puerta;
        this.precio = habitacion.precio;
        setIdentificador();
    }

    public void setIdentificador(){
        if (planta <= 0 || puerta < 0) {
            throw new IllegalArgumentException("ERROR: La planta y la puerta deben ser mayores que cero.");
        }
        this.identificador = String.format("%d%d", this.planta, this.puerta);
    }

    public abstract int getNUM_MAXIMO_PERSONAS();
    public String getIdentificador() {
            return identificador;
        }
        public int getPlanta() {
            return planta;
        }

        private void setPlanta(int planta){
            if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA) {
                throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que 1 ni mayor que 3.");
            }
            this.planta = planta;
            try {
                setIdentificador();
            }catch (NullPointerException e){
                System.out.println("ERROR: La planta de una habitación no puede ser nula.");
            }
        }
        public int getPuerta() {
            return puerta;
        }

        private void setPuerta(int puerta){
            if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA) {
                throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que 1 ni mayor que 15.");
            }
            this.puerta = puerta;
            setIdentificador();
        }

        public double getPrecio() {
            return precio;
        }
        public Habitacion(String identificador) {
            this.identificador = identificador;
        }
        private void setPrecio(double precio){
            if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION) {
                throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que 40.0 ni mayor que 150.0.");
            } else {
                this.precio = precio;
            }
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            Habitacion that = (Habitacion) o;
            return Objects.equals(identificador, that.identificador);
        }

    @Override
        public int hashCode() {
            return Objects.hash(identificador);
        }

        @Override
        public String toString() {
            return String.format("identificador=%s (%d-%d), precio habitación=%s",
                    identificador, planta, puerta, precio);
        }
}