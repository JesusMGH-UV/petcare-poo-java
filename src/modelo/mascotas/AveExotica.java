package modelo.mascotas;

import modelo.personas.Dueno;

public class AveExotica extends Mascota {

    private double longitudAlas;

    public AveExotica(String nombre, int edad, double peso, Dueno dueno, double longitudAlas) {
        super(nombre, edad, peso, dueno);
        this.longitudAlas = longitudAlas;
    }

    @Override
    public String emitirSonido() {
        return "Pio";
    }

    public double getLongitudAlas() {
        return longitudAlas;
    }
}
