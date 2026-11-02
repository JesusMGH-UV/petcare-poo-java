package app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.clinica.Cita;
import modelo.clinica.Clinica;
import modelo.clinica.Comprobante;
import modelo.clinica.Consultorio;
import modelo.mascotas.AveExotica;
import modelo.mascotas.Gato;
import modelo.mascotas.Mascota;
import modelo.mascotas.Perro;
import modelo.personas.Dueno;
import modelo.personas.Veterinario;
import modelo.servicios.Cirugia;
import modelo.servicios.ConsultaGeneral;
import modelo.servicios.ServicioCobrar;
import modelo.servicios.Vacunacion;

public class Menu {

    private Clinica clinica;
    private Entrada entrada;

    public Menu(Clinica clinica, Entrada entrada) {
        this.clinica = clinica;
        this.entrada = entrada;
    }

    public void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarDuenoYMascota();
                break;
            case 2:
                registrarVeterinario();
                break;
            case 3:
                agendarCita();
                break;
            case 4:
                atenderOCancelarCita();
                break;
            case 5:
                generarComprobante();
                break;
            case 6:
                consultarHistorial();
                break;
            case 7:
                listarMascotasYSonidos();
            case 8:
                verTotalPorVeterinario();
                break;
            case 9:
                darDeBajaMascota();
                break;
            default:
                System.out.println("Opcion no valida.");
                break;
        }
    }

    public void registrarDuenoYMascota() {
        try {
            Dueno dueno = obtenerOCrearDueno();
            String nombreMascota = entrada.leerTexto("Nombre de la mascota: ");
            int edad = entrada.leerEntero("Edad (anos): ");
            double peso = entrada.leerDecimal("Peso (kg): ");
            int tipo = entrada.leerEntero("Tipo (1=Perro, 2=Gato, 3=Ave exotica): ");

            Mascota mascota = crearMascotaPorTipo(tipo, nombreMascota, edad, peso, dueno);
            if (mascota != null) {
                clinica.registrarMascota(mascota);
                System.out.println("Mascota registrada: " + mascota);
            }
        } catch (Exception e) {
            System.out.println("Error al registrar mascota: " + e.getMessage());
        }
    }

    private Dueno obtenerOCrearDueno() {
        List<Dueno> duenos = clinica.getDuenos();
        if (!duenos.isEmpty() && entrada.leerConfirmacion("Usar dueno existente? (s/n): ")) {
            for (int i = 0; i < duenos.size(); i++) {
                System.out.println((i + 1) + ". " + duenos.get(i));
            }
            int idx = entrada.leerEntero("Numero de dueno: ") - 1;
            return duenos.get(idx);
        }
        String nombre = entrada.leerTexto("Nombre del dueno: ");
        String apellido = entrada.leerTexto("Apellido: ");
        String telefono = entrada.leerTexto("Telefono: ");
        String correo = entrada.leerTexto("Correo: ");
        String direccion = entrada.leerTexto("Direccion: ");
        Dueno nuevo = new Dueno(nombre, apellido, telefono, correo, direccion);
        clinica.registrarDueno(nuevo);
        return nuevo;
    }

    private Mascota crearMascotaPorTipo(int tipo, String nombre, int edad, double peso, Dueno dueno) {
        if (tipo == 1) {
            String raza = entrada.leerTexto("Raza: ");
            String energia = entrada.leerTexto("Nivel de energia: ");
            return new Perro(nombre, edad, peso, dueno, raza, energia);
        } else if (tipo == 2) {
            boolean domestico = entrada.leerConfirmacion("Es domestico? (s/n): ");
            return new Gato(nombre, edad, peso, dueno, domestico);
        } else if (tipo == 3) {
            double alas = entrada.leerDecimal("Longitud de alas (cm): ");
            return new AveExotica(nombre, edad, peso, dueno, alas);
        }
        System.out.println("Tipo de mascota no reconocido.");
        return null;
    }

    public void registrarVeterinario() {
        String nombre = entrada.leerTexto("Nombre del veterinario: ");
        String apellido = entrada.leerTexto("Apellido: ");
        String telefono = entrada.leerTexto("Telefono: ");
        String correo = entrada.leerTexto("Correo: ");
        String cedula = entrada.leerTexto("Cedula profesional: ");
        String especialidad = entrada.leerTexto("Especialidad: ");

        Veterinario vet = new Veterinario(nombre, apellido, telefono, correo, cedula, especialidad);
        clinica.contratarVeterinario(vet);
        System.out.println("Veterinario registrado: " + vet);
    }

    public void agendarCita() {
        if (clinica.getMascotas().isEmpty() || clinica.getVeterinarios().isEmpty()) {
            System.out.println("Debe haber al menos una mascota y un veterinario registrados.");
            return;
        }
        try {
            Mascota mascota = seleccionarMascota();
            Veterinario vet = seleccionarVeterinario();
            Consultorio consultorio = seleccionarConsultorio();
            LocalDateTime fechaHora = entrada.leerFechaHora("Fecha y hora (yyyy-MM-dd HH:mm): ");

            Cita cita = clinica.agendarCita(fechaHora, mascota, vet, consultorio);
            System.out.println("Cita agendada: " + cita);
        } catch (Exception e) {
            System.out.println("No se pudo agendar la cita: " + e.getMessage());
        }
    }

    public void atenderOCancelarCita() {
        List<Cita> citas = clinica.getCitas();
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }
        try {
            for (int i = 0; i < citas.size(); i++) {
                System.out.println((i + 1) + ". " + citas.get(i));
            }
            Cita cita = citas.get(entrada.leerEntero("Seleccione cita: ") - 1);
            int accion = entrada.leerEntero("Accion (1=Atender, 2=Cancelar): ");

            if (accion == 1) {
                registrarAtencionCita(cita);
            } else if (accion == 2) {
                cita.cancelar();
                System.out.println("Cita cancelada: " + cita.getIdCita());
            }
        } catch (Exception e) {
            System.out.println("Error al procesar cita: " + e.getMessage());
        }
    }

    private void registrarAtencionCita(Cita cita) {
        String diagnostico = entrada.leerTexto("Diagnostico: ");
        String tratamiento = entrada.leerTexto("Tratamiento: ");
        List<ServicioCobrar> servicios = capturarServicios();
        Comprobante comp = clinica.registrarAtencion(cita, diagnostico, tratamiento, servicios);
        System.out.println("Atencion registrada. Comprobante: " + comp.getFolio() + " ($" + comp.getTotal() + ")");
    }

    private List<ServicioCobrar> capturarServicios() {
        List<ServicioCobrar> lista = new ArrayList<>();
        int tipo = entrada.leerEntero("Servicio (1=Consulta general, 2=Vacunacion, 3=Cirugia): ");
        if (tipo == 1) {
            lista.add(new ConsultaGeneral(entrada.leerTexto("Motivo: ")));
        } else if (tipo == 2) {
            String vacuna = entrada.leerTexto("Nombre de la vacuna: ");
            double costo = entrada.leerDecimal("Costo de la vacuna: ");
            lista.add(new Vacunacion(150.0, vacuna, costo));
        } else if (tipo == 3) {
            double horas = entrada.leerDecimal("Horas de quirofano: ");
            double factor = entrada.leerDecimal("Factor de complejidad: ");
            lista.add(new Cirugia(1200.0, horas, factor));
        }
        return lista;
    }

    public void generarComprobante() {
        List<Comprobante> comprobantes = clinica.getComprobantes();
        if (comprobantes.isEmpty()) {
            System.out.println("No hay comprobantes generados.");
            return;
        }
        try {
            for (int i = 0; i < comprobantes.size(); i++) {
                System.out.println((i + 1) + ". " + comprobantes.get(i));
            }
            Comprobante comp = comprobantes.get(entrada.leerEntero("Seleccione comprobante: ") - 1);
            System.out.println(comp.imprimirDetalle());
        } catch (Exception e) {
            System.out.println("Seleccion invalida.");
        }
    }

    public void consultarHistorial() {
        if (clinica.getMascotas().isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }
        try {
            Mascota mascota = seleccionarMascota();
            if (mascota.getHistorial() != null) {
                System.out.println(mascota.getHistorial().obtenerResumen());
            } else {
                System.out.println("La mascota no cuenta con historial activo.");
            }
        } catch (Exception e) {
            System.out.println("Seleccion invalida.");
        }
    }

    public void listarMascotasYSonidos() {
        List<Mascota> mascotas = clinica.getMascotas();
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }
        for (Mascota m : mascotas) {
            System.out.println(m.getNombre() + " -> " + m.emitirSonido());
        }
    }

    public void verTotalPorVeterinario() {
        if (clinica.getVeterinarios().isEmpty()) {
            System.out.println("No hay veterinarios registrados.");
            return;
        }
        try {
            Veterinario vet = seleccionarVeterinario();
            List<Comprobante> cobros = clinica.consultarCobrosPorVeterinario(vet);
            double suma = 0;
            for (Comprobante c : cobros) {
                System.out.println("  " + c);
                suma += c.getTotal();
            }
            System.out.println("Total cobrado por " + vet.getNombreCompleto() + ": $" + suma);
        } catch (Exception e) {
            System.out.println("Seleccion invalida.");
        }
    }

    public void darDeBajaMascota() {
        if (clinica.getMascotas().isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }
        try {
            Mascota mascota = seleccionarMascota();
            mascota.darDeBaja();
            clinica.getMascotas().remove(mascota);
            System.out.println("Mascota dada de baja junto con su historial: " + mascota.getNombre());
        } catch (Exception e) {
            System.out.println("Seleccion invalida.");
        }
    }

    private Mascota seleccionarMascota() {
        List<Mascota> mascotas = clinica.getMascotas();
        for (int i = 0; i < mascotas.size(); i++) {
            System.out.println((i + 1) + ". " + mascotas.get(i));
        }
        return mascotas.get(entrada.leerEntero("Seleccione mascota: ") - 1);
    }

    private Veterinario seleccionarVeterinario() {
        List<Veterinario> veterinarios = clinica.getVeterinarios();
        for (int i = 0; i < veterinarios.size(); i++) {
            System.out.println((i + 1) + ". " + veterinarios.get(i));
        }
        return veterinarios.get(entrada.leerEntero("Seleccione veterinario: ") - 1);
    }

    private Consultorio seleccionarConsultorio() {
        List<Consultorio> consultorios = clinica.getConsultorios();
        for (int i = 0; i < consultorios.size(); i++) {
            System.out.println((i + 1) + ". " + consultorios.get(i));
        }
        return consultorios.get(entrada.leerEntero("Seleccione consultorio: ") - 1);
    }
}
