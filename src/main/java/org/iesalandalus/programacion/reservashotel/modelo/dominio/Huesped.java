package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {
    private static final String ER_TELEFONO = "^[0-9]{9}$";
    private static final String ER_CORREO = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$";
    private static final String ER_DNI = "^[0-9]{8}[A-Za-z]$";
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;
    public static final String FORMATO_FECHA = "dd/MM/yyyy";

    public Huesped(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento) {
        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }
    public Huesped(Huesped huesped) {
        if(huesped==null){
            throw new NullPointerException("ERROR: No es posible copiar un huésped nulo.");
        }
        this.nombre = huesped.nombre;
        this.dni = huesped.dni;
        this.correo = huesped.correo;
        this.telefono = huesped.telefono;
        this.fechaNacimiento = huesped.fechaNacimiento;
    }

    public Huesped(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = formateaNombre(nombre);
    }

    private String formateaNombre(String nombre) {
        if(nombre==null){
            throw new NullPointerException("ERROR: El nombre de un huésped no puede ser nulo.");
        }
        if(nombre.isBlank()){
            throw new IllegalArgumentException("ERROR: El nombre de un huésped no puede estar vacío.");
        }
        String[] palabras = nombre.split(" ");
        StringBuilder resultado = new StringBuilder();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)));
                resultado.append(palabra.substring(1).toLowerCase());
                resultado.append(" ");
            }
        }
        return resultado.toString().trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if(telefono==null){
            throw new NullPointerException("ERROR: El teléfono de un huésped no puede ser nulo.");
        }
        if (!telefono.matches(ER_TELEFONO)) {
            throw new IllegalArgumentException("Formato de teléfono no válido");
        }
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (!correo.matches(ER_CORREO)) {
            throw new IllegalArgumentException("Formato de correo no válido");
        }
        this.correo = correo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (!comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("Formato de DNI no válido");
        }
        this.dni = dni;
    }

    private boolean comprobarLetraDni(String dni) {
        Pattern pattern = Pattern.compile(ER_DNI);
        Matcher matcher = pattern.matcher(dni);
        if (matcher.matches()) {
            String numeros = dni.substring(0, 8);
            String letra = dni.substring(8);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int resto = Integer.parseInt(numeros) % 23;
            return letra.equalsIgnoreCase(String.valueOf(letras.charAt(resto)));
        }
        return false;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIniciales() {
        StringBuilder iniciales = new StringBuilder();
        for (String palabra : nombre.split(" ")) {
            if (!palabra.isEmpty()) {
                iniciales.append(palabra.charAt(0));
            }
        }
        return iniciales.toString();
    }

    @Override
    public boolean equals(Object comprobarHuesped) {
        if (this == comprobarHuesped) return true;
        if (comprobarHuesped == null || getClass() != comprobarHuesped.getClass()) return false;
        Huesped huesped = (Huesped) comprobarHuesped;
        return Objects.equals(dni, huesped.dni);
    }
    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return String.format("nombre=%s ("+ getIniciales() +"), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s",
                nombre, dni, correo, telefono, fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }
}
