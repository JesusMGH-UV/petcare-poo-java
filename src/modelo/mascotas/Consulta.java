package modelo.mascotas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.personas.Veterinario;
import modelo.servicios.ServicioCobrar;

public class Consulta {

    private LocalDateTime fecha;
    private String diagnostico;
    private String tratamiento;
    private Veterinario veterinario;
    private List<ServicioCobrar> serviciosAplicados;

    public Consulta(String hallazgoClinico, String recetaIndicada, Veterinario medicoEncargado, List<ServicioCobrar> cargosIniciales) {
        if (hallazgoClinico == null || hallazgoClinico.isBlank() || medicoEncargado == null) {
            throw new IllegalArgumentException("Toda consulta requiere un diagnostico escrito y el veterinario que atendio al paciente");
        }
        this.fecha = LocalDateTime.now();
        this.diagnostico = hallazgoClinico;
        this.tratamiento = recetaIndicada;
        this.veterinario = medicoEncargado;
        this.serviciosAplicados = cargosIniciales != null ? new ArrayList<>(cargosIniciales) : new ArrayList<>();
    }

    public void agregarServicio(ServicioCobrar cargoExtra) {
        this.serviciosAplicados.add(cargoExtra);
    }

    public double calcularTotalServicios() {
        double montoAcumulado = 0.0;
        for (ServicioCobrar item : serviciosAplicados) {
            montoAcumulado += item.calcularCosto();
        }
        return montoAcumulado;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public String getTratamiento() {
        return this.tratamiento;
    }

    public Veterinario getVeterinario() {
        return this.veterinario;
    }

    public List<ServicioCobrar> getServiciosAplicados() {
        return this.serviciosAplicados;
    }
}
