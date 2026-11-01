package modelo.servicios;

public class Cirugia implements ServicioCobrar {

    private double tarifaPorHora;
    private double horasQuirofano;
    private double factorComplejidad;

    public Cirugia(double costoHora, double duracionHoras, double nivelRiesgo) {
        if (costoHora < 0 || duracionHoras < 0 || nivelRiesgo < 0) {
            throw new IllegalArgumentException("Error en cirugia: tarifa, duracion y factor deben ser positivos");
        }
        this.tarifaPorHora = costoHora;
        this.horasQuirofano = duracionHoras;
        this.factorComplejidad = nivelRiesgo;
    }

    @Override
    public double calcularCosto() {
        return tarifaPorHora * horasQuirofano * factorComplejidad;
    }

    @Override
    public String getDescripcion() {
        return "Intervencion quirurgica - " + horasQuirofano + " hrs";
    }

    public double getTarifaPorHora() {
        return this.tarifaPorHora;
    }

    public double getHorasQuirofano() {
        return this.horasQuirofano;
    }

    public double getFactorComplejidad() {
        return this.factorComplejidad;
    }
}
