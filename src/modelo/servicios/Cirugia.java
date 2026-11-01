package modelo.servicios;

public class Cirugia implements ServicioCobrar {

    private double tarifaPorHora;
    private double horasQuirofano;
    private double factorComplejidad;

    public Cirugia(double tarifaPorHora, double horasQuirofano, double factorComplejidad) {
        if (tarifaPorHora < 0 || horasQuirofano < 0 || factorComplejidad < 0) {
            throw new IllegalArgumentException("Los valores de la cirugia no pueden ser negativos");
        }
        this.tarifaPorHora = tarifaPorHora;
        this.horasQuirofano = horasQuirofano;
        this.factorComplejidad = factorComplejidad;
    }

    @Override
    public double calcularCosto() {
        return (tarifaPorHora + horasQuirofano) * factorComplejidad;
    }

    @Override
    public String getDescripcion() {
        return "Cirugia (" + horasQuirofano + " h de quirofano)";
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public double getHorasQuirofano() {
        return horasQuirofano;
    }

    public double getFactorComplejidad() {
        return factorComplejidad;
    }
}
