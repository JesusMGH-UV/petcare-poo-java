package modelo.mascotas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistorialClinico {

    private String codigoHistorial;
    private List<Consulta> consultas = new ArrayList<>();   

    public HistorialClinico(String claveExpediente) {
        this.codigoHistorial = claveExpediente;
    }

    public void agregarConsulta(Consulta nuevaVisita) {
        this.consultas.add(nuevaVisita);
    }

    public List<Consulta> getConsultas() {
        return Collections.unmodifiableList(this.consultas);
    }

    public String obtenerResumen() {
        if (consultas.isEmpty()) {
            return "Expediente " + codigoHistorial + " (sin visitas previas)";
        }
        StringBuilder texto = new StringBuilder("Expediente clinico [" + codigoHistorial + "]:\n");
        for (Consulta visita : consultas) {
            texto.append("* [").append(visita.getFecha().toLocalDate()).append("] Diagnostico: ").append(visita.getDiagnostico()).append(" -> Tratamiento: ").append(visita.getTratamiento()).append(" (Atendio: ").append(visita.getVeterinario().getNombreCompleto()).append(")\n");
        }
        return texto.toString();
    }

    public String getCodigoHistorial() {
        return this.codigoHistorial;
    }
}
