package modelo.personas;

public class Dueno extends Persona {

    private String direccion;

    public Dueno(String nombre, String apellido, String telefono, String correo, String direccion) {
        super(nombre, apellido, telefono, correo);
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }
}
