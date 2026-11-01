package modelo.clinica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.mascotas.Mascota;
import modelo.personas.Veterinario;
import modelo.servicios.ServicioCobrar;

public class Comprobante {

    private static int contadorFolios = 0;

    private String folio;
    private LocalDateTime fechaEmision;
    private Cita citaAsociada;
    private List<ServicioCobrar> servicios;
    private double total;

    public Comprobante(Cita citaAsociada, List<ServicioCobrar> servicios) {
        this.folio = "CMP-" + String.format("%04d", ++contadorFolios);
        this.fechaEmision = LocalDateTime.now();
        this.citaAsociada = citaAsociada;
        this.servicios = new ArrayList<>(servicios);
        this.total = calcularTotal();
    }

    public double calcularTotal() {
        double suma = 0;
        for (ServicioCobrar s : servicios) {
            suma += s.calcularCosto();
        }
        return suma;
    }

    public String imprimirDetalle() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comprobante ").append(folio).append(" - ").append(fechaEmision.toLocalDate()).append("\n");
        sb.append("Mascota: ").append(getMascota().getNombre()).append("\n");
        sb.append("Veterinario: ").append(getVeterinario().getNombreCompleto()).append("\n");
        for (ServicioCobrar s : servicios) {
            sb.append(" - ").append(s.getDescripcion()).append(": $").append(s.calcularCosto()).append("\n");
        }
        sb.append("Total: $").append(total);
        return sb.toString();
    }

    public Mascota getMascota() {
        return citaAsociada.getMascota();
    }

    public Veterinario getVeterinario() {
        return citaAsociada.getVeterinario();
    }

    public String getFolio() {
        return folio;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public Cita getCitaAsociada() {
        return citaAsociada;
    }

    public List<ServicioCobrar> getServicios() {
        return servicios;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return folio + " | " + getMascota().getNombre() + " | $" + total;
    }
}
