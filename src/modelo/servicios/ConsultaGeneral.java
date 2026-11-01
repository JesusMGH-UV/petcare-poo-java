package modelo.servicios;

public class ConsultaGeneral implements ServicioCobrar {

    private double tarifaBase;
    private String motivo;

    public ConsultaGeneral() {
        this(350.0, "Revision general");
    }

    public ConsultaGeneral(double montoBase, String motivoConsulta) {
        if (montoBase < 0) {
            throw new IllegalArgumentException("Monto invalido: la tarifa base no admite numeros negativos");
        }
        this.tarifaBase = montoBase;
        this.motivo = motivoConsulta;
    }

    public ConsultaGeneral(String motivoConsulta) {
        this(350.0, motivoConsulta);
    }

    @Override
    public double calcularCosto() {
        return this.tarifaBase;
    }

    @Override
    public String getDescripcion() {
        return "Consulta general (" + motivo + ")";
    }

    public double getTarifaBase() {
        return this.tarifaBase;
    }

    public String getMotivo() {
        return this.motivo;
    }
}
