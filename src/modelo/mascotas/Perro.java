package modelo.mascotas;

import modelo.personas.Dueno;

public class Perro extends Mascota {

    private String raza;
    private String nivelEnergia;

    public Perro(String nombre, int edad, double peso, Dueno dueno, String raza, String nivelEnergia) {
        super(nombre, edad, peso, dueno);
        this.raza = raza;
        this.nivelEnergia = nivelEnergia;
    }

    public Perro(String nombre, int edad, double peso, Dueno dueno) {
        this(nombre, edad, peso, dueno, "Mestizo", "Media");
    }

    @Override
    public String emitirSonido() {
        return "Guau";
    }

    public String getRaza() {
        return raza;
    }

    public String getNivelEnergia() {
        return nivelEnergia;
    }
}
