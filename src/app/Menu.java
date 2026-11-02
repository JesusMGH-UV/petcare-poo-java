package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    private Scanner scanner;
    private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Menu(Clinica clinica, Scanner scanner) {
        this.clinica = clinica;
        this.scanner = scanner;
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
            default:
                System.out.println("Opcion no valida.");
                break;
        }
    }

    public void registrarDuenoYMascota() {
        try {
            Dueno dueno = obtenerOCrearDueno();
            System.out.print("Nombre de la mascota: ");
            String nombreMascota = scanner.nextLine().trim();
            System.out.print("Edad (anos): ");
            int edad = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Peso (kg): ");
            double peso = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Tipo (1=Perro, 2=Gato, 3=Ave exotica): ");
            int tipo = Integer.parseInt(scanner.nextLine().trim());

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
        if (!duenos.isEmpty()) {
            System.out.print("Usar dueno existente? (s/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                for (int i = 0; i < duenos.size(); i++) {
                    System.out.println((i + 1) + ". " + duenos.get(i));
                }
                System.out.print("Numero de dueno: ");
                int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                return duenos.get(idx);
            }
        }
        System.out.print("Nombre del dueno: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine().trim();
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        System.out.print("Direccion: ");
        String direccion = scanner.nextLine().trim();
        Dueno nuevo = new Dueno(nombre, apellido, telefono, correo, direccion);
        clinica.registrarDueno(nuevo);
        return nuevo;
    }

    private Mascota crearMascotaPorTipo(int tipo, String nombre, int edad, double peso, Dueno dueno) {
        if (tipo == 1) {
            System.out.print("Raza: ");
            String raza = scanner.nextLine().trim();
            System.out.print("Nivel de energia: ");
            String energia = scanner.nextLine().trim();
            return new Perro(nombre, edad, peso, dueno, raza, energia);
        } else if (tipo == 2) {
            System.out.print("Es domestico? (s/n): ");
            boolean domestico = scanner.nextLine().trim().equalsIgnoreCase("s");
            return new Gato(nombre, edad, peso, dueno, domestico);
        } else if (tipo == 3) {
            System.out.print("Longitud de alas (cm): ");
            double alas = Double.parseDouble(scanner.nextLine().trim());
            return new AveExotica(nombre, edad, peso, dueno, alas);
        }
        System.out.println("Tipo de mascota no reconocido.");
        return null;
    }

    public void registrarVeterinario() {
        System.out.print("Nombre del veterinario: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine().trim();
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        System.out.print("Cedula profesional: ");
        String cedula = scanner.nextLine().trim();
        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine().trim();

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
            System.out.print("Fecha y hora (yyyy-MM-dd HH:mm): ");
            LocalDateTime fechaHora = LocalDateTime.parse(scanner.nextLine().trim(), formatoFecha);

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
            System.out.print("Seleccione cita: ");
            Cita cita = citas.get(Integer.parseInt(scanner.nextLine().trim()) - 1);
            System.out.print("Accion (1=Atender, 2=Cancelar): ");
            int accion = Integer.parseInt(scanner.nextLine().trim());

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
        System.out.print("Diagnostico: ");
        String diagnostico = scanner.nextLine().trim();
        System.out.print("Tratamiento: ");
        String tratamiento = scanner.nextLine().trim();
        List<ServicioCobrar> servicios = capturarServicios();
        Comprobante comp = clinica.registrarAtencion(cita, diagnostico, tratamiento, servicios);
        System.out.println("Atencion registrada. Comprobante: " + comp.getFolio() + " ($" + comp.getTotal() + ")");
    }

    private List<ServicioCobrar> capturarServicios() {
        List<ServicioCobrar> lista = new ArrayList<>();
        System.out.print("Servicio (1=Consulta general, 2=Vacunacion, 3=Cirugia): ");
        int tipo = Integer.parseInt(scanner.nextLine().trim());
        if (tipo == 1) {
            System.out.print("Motivo: ");
            lista.add(new ConsultaGeneral(scanner.nextLine().trim()));
        } else if (tipo == 2) {
            System.out.print("Nombre de la vacuna: ");
            String vacuna = scanner.nextLine().trim();
            System.out.print("Costo de la vacuna: ");
            double costo = Double.parseDouble(scanner.nextLine().trim());
            lista.add(new Vacunacion(150.0, vacuna, costo));
        } else if (tipo == 3) {
            System.out.print("Horas de quirofano: ");
            double horas = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Factor de complejidad: ");
            double factor = Double.parseDouble(scanner.nextLine().trim());
            lista.add(new Cirugia(1200.0, horas, factor));
        }
        return lista;
    }

    private Mascota seleccionarMascota() {
        List<Mascota> mascotas = clinica.getMascotas();
        for (int i = 0; i < mascotas.size(); i++) {
            System.out.println((i + 1) + ". " + mascotas.get(i));
        }
        System.out.print("Seleccione mascota: ");
        return mascotas.get(Integer.parseInt(scanner.nextLine().trim()) - 1);
    }

    private Veterinario seleccionarVeterinario() {
        List<Veterinario> veterinarios = clinica.getVeterinarios();
        for (int i = 0; i < veterinarios.size(); i++) {
            System.out.println((i + 1) + ". " + veterinarios.get(i));
        }
        System.out.print("Seleccione veterinario: ");
        return veterinarios.get(Integer.parseInt(scanner.nextLine().trim()) - 1);
    }

    private Consultorio seleccionarConsultorio() {
        List<Consultorio> consultorios = clinica.getConsultorios();
        for (int i = 0; i < consultorios.size(); i++) {
            System.out.println((i + 1) + ". " + consultorios.get(i));
        }
        System.out.print("Seleccione consultorio: ");
        return consultorios.get(Integer.parseInt(scanner.nextLine().trim()) - 1);
    }
}
