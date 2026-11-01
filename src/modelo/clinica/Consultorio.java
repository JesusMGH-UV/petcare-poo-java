package modelo.clinica;

public class Consultorio {

    private int numero;

    public Consultorio(int numAsignado) {
        if (numAsignado <= 0) {
            throw new IllegalArgumentException("Numero de consultorio fuera de rango (requiere valor > 0)");
        }
        this.numero = numAsignado;
    }

    public int getNumero() {
        return this.numero;
    }

    @Override
    public String toString() {
        return "Consultorio #" + numero;
    }
}
