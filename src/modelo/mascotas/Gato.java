package modelo.mascotas;

import modelo.persona.Dueno;

public class Gato extends Mascota {

    private boolean esDomestico;

    public Gato(String nombre, int edad, double peso, Dueno dueno, boolean esDomestico) {
        super(nombre, edad, peso, dueno);
        this.esDomestico = esDomestico;
    }

    @Override
    public String emitirSonido() {
        return "Miau";
    }

    public boolean isEsDomestico() {
        return esDomestico;
    }
}
