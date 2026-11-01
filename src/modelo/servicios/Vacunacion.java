package modelo.servicios;

public class Vacunacion implements ServicioCobrar {

    private double tarifaBaseAplicacion;
    private String nombreVacuna;
    private double costoVacuna;

    public Vacunacion(double cargoAplicacion, String biologico, double precioDosis) {
        if (cargoAplicacion < 0 || precioDosis < 0) {
            throw new IllegalArgumentException("Ni la aplicacion ni la dosis pueden tener costo menor a cero");
        }
        this.tarifaBaseAplicacion = cargoAplicacion;
        this.nombreVacuna = biologico;
        this.costoVacuna = precioDosis;
    }

    @Override
    public double calcularCosto() {
        return this.tarifaBaseAplicacion + this.costoVacuna;
    }

    @Override
    public String getDescripcion() {
        return "Aplicacion de vacuna: " + nombreVacuna;
    }

    public double getTarifaBaseAplicacion() {
        return this.tarifaBaseAplicacion;
    }

    public String getNombreVacuna() {
        return this.nombreVacuna;
    }

    public double getCostoVacuna() {
        return this.costoVacuna;
    }
}
