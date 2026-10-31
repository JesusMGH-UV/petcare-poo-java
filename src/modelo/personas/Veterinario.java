package modelo.personas;

public class Veterinario extends Persona {

    private String cedulaProfesional;
    private String especialidad;

    public Veterinario(String nombre, String apellido, String telefono, String correo, String cedulaProfesional, String especialidad) {
        super(nombre, apellido, telefono, correo);
        this.cedulaProfesional = cedulaProfesional;
        this.especialidad = especialidad;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String toString() {
        return "Dr. " + super.toString() + " | " + especialidad;
    }
}
