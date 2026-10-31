package modelo.personas;

public class Recepcionista extends Persona {

    private String turno;

    public Recepcionista(String nombre, String apellido, String telefono, String correo, String turno) {
        super(nombre, apellido, telefono, correo);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    public void cambiarTurno(String nuevoTurno) {
        if (nuevoTurno == null || nuevoTurno.isBlank()) {
            throw new IllegalArgumentException("El turno no puede estar vacío");
        }
        this.turno = nuevoTurno;
    }

    @Override
    public String toString() {
        return super.toString() + " | turno: " + turno;
    }
}
