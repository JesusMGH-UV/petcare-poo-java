package modelo.clinica;

public class Consultorio {

    private int numero;

    public Consultorio(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El numero de consultorio debe ser mayor a 0");
        }
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Consultorio " + numero;
    }
}
