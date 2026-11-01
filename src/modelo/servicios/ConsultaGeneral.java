package modelo.servicios;

public class ConsultaGeneral implements ServicioCobrar {

    private double tarifaBase;
    private String motivo;

    public ConsultaGeneral() {
        this(350.0, "Revision general");
    }

    public ConsultaGeneral(double tarifaBase, String motivo) {
        if (tarifaBase < 0) {
            throw new IllegalArgumentException("La tarifa base no puede ser negativa");
        }
        this.tarifaBase = tarifaBase;
        this.motivo = motivo;
    }

    public ConsultaGeneral(String motivo) {
        this(350.0, motivo);
    }

    @Override
    public double calcularCosto() {
        return tarifaBase;
    }

    @Override
    public String getDescripcion() {
        return "Consulta general: " + motivo;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public String getMotivo() {
        return motivo;
    }
}
