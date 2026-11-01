package modelo.mascotas;

import modelo.personas.Dueno;

public abstract class Mascota {

    protected String nombre;
    protected int edad;
    protected double peso;
    protected Dueno dueno;
    private HistorialClinico historial;
    private static int secuenciaExpedientes = 0;

    protected Mascota(String nombre, int edad, double peso, Dueno dueno) {
        this.nombre = nombre;
        this.dueno = dueno;
        secuenciaExpedientes++;
        this.historial = new HistorialClinico("EXP-" + secuenciaExpedientes);
        setEdad(edad);
        setPeso(peso);
    }

    public abstract String emitirSonido();

    public void setEdad(int nuevaEdad) {
        if (nuevaEdad <= 0) {
            throw new IllegalArgumentException("Edad invalida: tiene que ser mayor a 0");
        }
        this.edad = nuevaEdad;
    }

    public void setPeso(double nuevoPeso) {
   if (nuevoPeso <= 0) {
            throw new IllegalArgumentException("El peso registrado no puede ser menor o igual a 0");
        }
        this.peso = nuevoPeso;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getEdad() {
        return this.edad;
    }

    public double getPeso() {
        return this.peso;
    }

    public Dueno getDueno() {
        return this.dueno;
    }

    public HistorialClinico getHistorial() {
        return historial;
    }

    public void darDeBaja() {
        this.historial = null;
    }

    @Override
    public String toString() {
        return nombre + " (" + edad + " años, " + peso + " kg) - Dueño: " + dueno.getNombreCompleto();
    }
}
