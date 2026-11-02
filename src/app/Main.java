package app;

import java.util.Scanner;
import modelo.clinica.Clinica;

public class Main {

    public static void main(String[] args) {
        Clinica clinica = new Clinica("PetCare");
        clinica.registrarConsultorio(1);
        clinica.registrarConsultorio(2);

        Scanner scanner = new Scanner(System.in);
        Entrada entrada = new Entrada(scanner);
        Menu menu = new Menu(clinica, entrada);
        boolean activo = true;

        while (activo) {
            System.out.println("\n--- Clinica Veterinaria " + clinica.getNombre() + " ---");
            System.out.println("1. Registrar dueno y mascota");
            System.out.println("2. Registrar veterinario");
            System.out.println("3. Agendar cita");
            System.out.println("4. Atender o cancelar cita");
            System.out.println("5. Generar comprobante");
            System.out.println("6. Consultar historial clinico");
            System.out.println("7. Listar mascotas y sonidos");
            System.out.println("8. Ver total cobrado por veterinario");
            System.out.println("9. Dar de baja una mascota");
            System.out.println("0. Salir");

            int opcion = entrada.leerEntero("Seleccione una opcion: ");
            if (opcion == 0) {
                System.out.println("Saliendo del sistema...");
                activo = false;
            } else {
                menu.procesarOpcion(opcion);
            }
        }
    }
}
