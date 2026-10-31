package modelo.personas;

public abstract class Persona {

    protected String nombre;
    protected String apellido;
    protected String telefono;
    protected String correo;

    protected Persona(String nombre, String apellido, String telefono, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void actualizarContacto(String tel, String correo) {
        if (tel == null || tel.isBlank() || correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El teléfono y el correo no pueden estar vacíos");
        }
        this.telefono = tel;
        this.correo = correo;
    }
}
