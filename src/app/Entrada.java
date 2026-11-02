package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Entrada {

    private Scanner scanner;
    private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Entrada(Scanner scanner) {
        this.scanner = scanner;
    }

    public String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("El dato no puede estar vacio.");
        }
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida: debe ingresar un numero entero.");
            }
        }
    }

    public double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida: debe ingresar un numero.");
            }
        }
    }

    public boolean leerConfirmacion(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim().toLowerCase();
            if (linea.equals("s") || linea.equals("si")) {
                return true;
            }
            if (linea.equals("n") || linea.equals("no")) {
                return false;
            }
            System.out.println("Responda con 's' o 'n'.");
        }
    }

    public LocalDateTime leerFechaHora(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return LocalDateTime.parse(linea, formatoFecha);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha invalido. Use yyyy-MM-dd HH:mm.");
            }
        }
    }
}
