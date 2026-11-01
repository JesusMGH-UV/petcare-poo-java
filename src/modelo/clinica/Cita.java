package modelo.clinica;

import java.time.LocalDateTime;
import modelo.mascotas.Mascota;
import modelo.personas.Veterinario;

public class Cita {

    private String idCita;
    private LocalDateTime fechaHora;
    private EstadoCita estado;
    private Mascota mascota;
    private Veterinario veterinario;
    private Consultorio consultorio;

    public Cita(String idCita, LocalDateTime fechaHora, Mascota mascota, Veterinario veterinario, Consultorio consultorio) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.consultorio = consultorio;
        this.estado = EstadoCita.PROGRAMADA;
    }

    public void marcarComoAtendida() {
        if (estado == EstadoCita.CANCELADA) {
            throw new IllegalStateException("Una cita cancelada no puede atenderse");
        }
        if (estado == EstadoCita.ATENDIDA) {
            throw new IllegalStateException("La cita ya fue atendida");
        }
        estado = EstadoCita.ATENDIDA;
    }

    public void cancelar() {
        if (estado == EstadoCita.ATENDIDA) {
            throw new IllegalStateException("Una cita atendida no puede cancelarse");
        }
        estado = EstadoCita.CANCELADA;
    }

    public String getIdCita() {
        return idCita;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    @Override
    public String toString() {
        return idCita + " | " + fechaHora + " | " + mascota.getNombre() + " | Dr(a). "
                + veterinario.getNombreCompleto() + " | " + consultorio + " | " + estado;
    }
}
