# Análisis de PetCare

## 1.1 Tabla de clases candidatas

| Clase candidata | Tipo | Atributos principales | Responsabilidades (métodos) | Justificación |
|---|---|---|---|---|
| Persona | Abstracta | nombre, apellido, telefono, correo | getNombreCompleto(), actualizarContacto() | Agrupa los datos básicos de contacto. Tanto dueños como veterinarios y recepcionistas heredan de aquí para no repetir campos. |
| Dueno | Concreta | direccion | getDireccion() | Representa al cliente responsable de las mascotas. Únicamente añade el domicilio particular sobre los datos de persona. |
| Veterinario | Concreta | cedulaProfesional, especialidad | getCedulaProfesional(), getEspecialidad() | Médico que atiende las consultas; su registro puede existir de manera independiente aunque la clínica deje de operar. |
| Recepcionista | Concreta | turno | cambiarTurno() | Empleado administrativo del mostrador. Solo necesita controlar en qué horario trabaja. |
| Mascota | Abstracta | nombre, edad, peso, dueno, historial | emitirSonido(), setEdad(), setPeso() | Base común para cualquier paciente animal, dejando abierto el sonido que hace cada especie. |
| Perro | Concreta | raza, nivelEnergia | emitirSonido() | Subclase de mascota. Distingue la raza del perro y qué tan activo es. |
| Gato | Concreta | esDomestico | emitirSonido() | Paciente felino. Indica mediante una bandera si vive en casa o no. |
| AveExotica | Concreta | longitudAlas | emitirSonido() | Variante para aves que guarda cuánto miden sus alas y define su propio sonido. |
| HistorialClinico | Concreta | codigoHistorial, consultas | agregarConsulta(), obtenerResumen() | Expediente médico ligado por completo a una mascota; si el animal se da de baja, este historial desaparece con él. |
| Consulta | Concreta | fecha, diagnostico, tratamiento, veterinario, serviciosAplicados | agregarServicio(), calcularTotalServicios() | Guarda el detalle clínico de una revisión ya realizada junto con la lista de servicios que se le hicieron al paciente. |
| ServicioCobrar | Interfaz | (ninguno) | calcularCosto(), getDescripcion() | Contrato común de cobro. Obliga a que cada tipo de servicio implemente su propia fórmula para sacar el precio. |
| ConsultaGeneral | Concreta | tarifaBase, motivo | calcularCosto(), getDescripcion() | Revisión estándar. Cobra directamente una tarifa fija sin cargos extra. |
| Vacunacion | Concreta | tarifaBaseAplicacion, nombreVacuna, costoVacuna | calcularCosto(), getDescripcion() | Suma el precio de poner la inyección más lo que cuesta el biológico aplicado. |
| Cirugia | Concreta | tarifaPorHora, horasQuirofano, factorComplejidad | calcularCosto(), getDescripcion() | Operación quirúrgica cuyo monto final se calcula multiplicando tiempo de quirófano, tarifa por hora y grado de dificultad. |
| Cita | Concreta | idCita, fechaHora, estado, mascota, veterinario, consultorio | marcarComoAtendida(), cancelar() | Conecta en una fecha y hora específicas al paciente, al médico asignado y al consultorio donde se dará la atención. |
| EstadoCita | Enumeración | PROGRAMADA, ATENDIDA, CANCELADA | (ninguno) | Restringe el estado de una cita a tres valores fijos. |
| Consultorio | Concreta | numero | getNumero() | Cuarto físico numerado dentro de las instalaciones de la clínica. |
| Comprobante | Concreta | folio, fechaEmision, citaAsociada, servicios, total | calcularTotal(), imprimirDetalle() | Recibo emitido al cerrar la atención de una cita para desglosar los servicios y el monto total a pagar. |
| Clinica | Concreta | nombre, consultorios, veterinarios, recepcionistas, duenos, mascotas, citas, comprobantes | agendarCita(), registrarAtencion(), buscarMascota(), consultarCobrosPorMascota(), consultarCobrosPorVeterinario() | Clase principal del sistema. Concentra las listas de objetos y valida las reglas de negocio al agendar o cobrar. |
