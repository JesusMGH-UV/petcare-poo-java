package modelo.servicios;

public class Vacunacion implements ServicioCobrar {

    private double tarifaBaseAplicacion;
    private String nombreVacuna;
    private double costoVacuna;

    public Vacunacion(double tarifaBaseAplicacion, String nombreVacuna, double costoVacuna) {
        if (tarifaBaseAplicacion < 0 || costoVacuna < 0) {
            throw new IllegalArgumentException("Los costos de vacunacion no pueden ser negativos");
        }
        this.tarifaBaseAplicacion = tarifaBaseAplicacion;
        this.nombreVacuna = nombreVacuna;
        this.costoVacuna = costoVacuna;
    }

    @Override
    public double calcularCosto() {
        return tarifaBaseAplicacion + costoVacuna;
    }

    @Override
    public String getDescripcion() {
        return "Vacunacion: " + nombreVacuna;
    }

    public double getTarifaBaseAplicacion() {
        return tarifaBaseAplicacion;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public double getCostoVacuna() {
        return costoVacuna;
    }
}
