package modelo.clinica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.mascotas.Consulta;
import modelo.mascotas.Mascota;
import modelo.personas.Dueno;
import modelo.personas.Recepcionista;
import modelo.personas.Veterinario;
import modelo.servicios.ServicioCobrar;

public class Clinica {

    private String nombre;
    private List<Consultorio> consultorios = new ArrayList<>();
    private List<Veterinario> veterinarios = new ArrayList<>();
    private List<Recepcionista> recepcionistas = new ArrayList<>();
    private List<Dueno> duenos = new ArrayList<>();
    private List<Mascota> mascotas = new ArrayList<>();
    private List<Cita> citas = new ArrayList<>();
    private List<Comprobante> comprobantes = new ArrayList<>();

    public Clinica() {
        this("PetCare");
    }

    public Clinica(String nombre) {
        this.nombre = nombre;
    }

    public void registrarConsultorio(int numero) {
        consultorios.add(new Consultorio(numero));
    }

    public void contratarVeterinario(Veterinario vet) {
        veterinarios.add(vet);
    }

    public void contratarRecepcionista(Recepcionista r) {
        recepcionistas.add(r);
    }

    public void registrarDueno(Dueno d) {
        duenos.add(d);
    }

    public void registrarMascota(Mascota m) {
        mascotas.add(m);
    }

    public Cita agendarCita(LocalDateTime fechaHora, Mascota mascota, Veterinario veterinario, Consultorio consultorio) {
        if (!validarDisponibilidad(veterinario, consultorio, fechaHora)) {
            throw new IllegalStateException("No hay disponibilidad para esa fecha");
        }
        Cita cita = new Cita("CITA-" + String.format("%03d", citas.size() + 1), fechaHora, mascota, veterinario, consultorio);
        citas.add(cita);
        return cita;
    }

    public Comprobante registrarAtencion(Cita cita, String diagnostico, String tratamiento, List<ServicioCobrar> servicios) {
        cita.marcarComoAtendida();
        Consulta consulta = new Consulta(diagnostico, tratamiento, cita.getVeterinario(), servicios);
        cita.getMascota().getHistorial().agregarConsulta(consulta);
        Comprobante comprobante = new Comprobante(cita, servicios);
        comprobantes.add(comprobante);
        return comprobante;
    }

    public List<Mascota> buscarMascota(String nombre) {
        List<Mascota> resultado = new ArrayList<>();
        for (Mascota m : mascotas) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public List<Mascota> buscarMascota(Dueno dueno) {
        List<Mascota> resultado = new ArrayList<>();
        for (Mascota m : mascotas) {
            if (m.getDueno() == dueno) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Consultorio> getConsultorios() {
        return consultorios;
    }

    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public List<Recepcionista> getRecepcionistas() {
        return recepcionistas;
    }

    public List<Dueno> getDuenos() {
        return duenos;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public List<Comprobante> getComprobantes() {
        return comprobantes;
    }

    @Override
    public String toString() {
        return nombre + " (" + consultorios.size() + " consultorios, " + veterinarios.size() + " veterinarios, " + citas.size() + " citas)";
    }
}
