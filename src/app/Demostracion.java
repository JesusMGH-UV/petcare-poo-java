package app;

import java.time.LocalDateTime;
import java.util.Arrays;
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

public class Demostracion {

    public static void main(String[] args) {
        ejecutar();
    }

    public static void ejecutar() {
        Clinica clinica = new Clinica("PetCare");
        clinica.registrarConsultorio(1);
        clinica.registrarConsultorio(2);

        Dueno ana = new Dueno("Ana", "Lopez", "2281234567", "ana.lopez@correo.com", "Calle Hidalgo 45, Xalapa");
        Dueno carlos = new Dueno("Carlos", "Mendez", "2289876543", "carlos.m@correo.com", "Av. Avila Camacho 120, Xalapa");
        clinica.registrarDueno(ana);
        clinica.registrarDueno(carlos);

        Mascota firulais = new Perro("Firulais", 4, 18.5, ana, "Labrador", "Alto");
        Mascota michi = new Gato("Michi", 2, 4.2, ana, true);
        Mascota kiwi = new AveExotica("Kiwi", 1, 0.35, carlos, 28.0);
        clinica.registrarMascota(firulais);
        clinica.registrarMascota(michi);
        clinica.registrarMascota(kiwi);

        Veterinario drRamirez = new Veterinario("Roberto", "Ramirez", "2281112233", "rramirez@petcare.com", "CED-90123", "Medicina general");
        Veterinario draSoto = new Veterinario("Elena", "Soto", "2284445566", "esoto@petcare.com", "CED-90456", "Cirugia y fauna exotica");
        clinica.contratarVeterinario(drRamirez);
        clinica.contratarVeterinario(draSoto);

        Consultorio c1 = clinica.getConsultorios().get(0);
        Consultorio c2 = clinica.getConsultorios().get(1);

        LocalDateTime fh1 = LocalDateTime.of(2026, 11, 3, 10, 30);
        LocalDateTime fh2 = LocalDateTime.of(2026, 11, 3, 12, 0);
        LocalDateTime fh3 = LocalDateTime.of(2026, 11, 4, 9, 0);

        Cita cita1 = clinica.agendarCita(fh1, firulais, drRamirez, c1);
        Cita cita2 = clinica.agendarCita(fh2, kiwi, draSoto, c2);
        Cita cita3 = clinica.agendarCita(fh3, michi, drRamirez, c1);

        System.out.println("=== Citas agendadas ===");
        System.out.println(cita1);
        System.out.println(cita2);
        System.out.println(cita3);

        System.out.println("\n=== Prueba de regla de negocio (conflicto de horario) ===");
        try {
            clinica.agendarCita(fh1, michi, drRamirez, c2);
        } catch (IllegalStateException e) {
            System.out.println("Error capturado correctamente: " + e.getMessage());
        }

        List<ServicioCobrar> serviciosCita1 = Arrays.asList(
                new ConsultaGeneral(350.0, "Revision anual"),
                new Vacunacion(150.0, "Rabia", 220.0)
        );
        Comprobante comp1 = clinica.registrarAtencion(cita1, "Paciente sano, refuerzo anual", "Vacuna antirrabica aplicada", serviciosCita1);

        List<ServicioCobrar> serviciosCita2 = Arrays.asList(
                new ConsultaGeneral(350.0, "Valoracion preoperatoria"),
                new Cirugia(1200.0, 1.5, 1.2)
        );
        Comprobante comp2 = clinica.registrarAtencion(cita2, "Fractura leve en ala derecha", "Fijacion quirurgica y reposo", serviciosCita2);

        System.out.println("\n=== Comprobantes emitidos ===");
        System.out.println(comp1.imprimirDetalle());
        System.out.println();
        System.out.println(comp2.imprimirDetalle());

        System.out.println("\n=== Mascotas registradas y sonidos (polimorfismo) ===");
        for (Mascota m : clinica.getMascotas()) {
            System.out.println(m.getNombre() + " (" + m.getClass().getSimpleName() + "): " + m.emitirSonido());
        }

        System.out.println("\n=== Total cobrado por veterinario ===");
        for (Veterinario v : clinica.getVeterinarios()) {
            double totalVet = 0;
            for (Comprobante c : clinica.consultarCobrosPorVeterinario(v)) {
                totalVet += c.getTotal();
            }
            System.out.println(v.getNombreCompleto() + ": $" + totalVet);
        }

        System.out.println("\n=== Historial clinico de " + firulais.getNombre() + " ===");
        System.out.println(firulais.getHistorial().obtenerResumen());
    }
}
