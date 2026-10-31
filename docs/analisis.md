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

## Objetos de ejemplo

| Objeto | Clase | Valores |
|---|---|---|
| duenoAna | Dueno | nombre = "Ana", apellido = "Lopez", telefono = "2281234567", correo = "ana.lopez@correo.com", direccion = "Calle Hidalgo 45, Xalapa" |
| firulais | Perro | nombre = "Firulais", edad = 4, peso = 18.5, dueno = duenoAna, raza = "Labrador", nivelEnergia = "Alto" |
| cita1 | Cita | idCita = "C-001", fechaHora = 2026-11-03 10:30, estado = PROGRAMADA, mascota = firulais, veterinario = drRamirez, consultorio = consultorio 2 |

## Clases descartadas

- **Raza:** No realiza ninguna operación por su cuenta; únicamente describe una característica del canino, por lo que basta con manejarlo como un atributo `String` dentro de `Perro`.
- **Sonido:** Más que un objeto con estado o datos propios, representa una acción que ejecuta cada animal. Queda cubierto directamente mediante el método polimórfico `emitirSonido()`.

## 1.3 Justificación de relaciones

### Composición

| Relación | Justificación |
|---|---|
| Clinica y Consultorio | La propia clínica instancia sus consultorios internamente y controla por completo su ciclo de vida. Se descarta la agregación porque un consultorio físico carece de sentido fuera del establecimiento que lo contiene; al desaparecer la clínica, también se destruyen sus consultorios. |
| Mascota e HistorialClinico | Cada expediente nace en el momento en que se instancia la mascota. No puede existir huérfano ni compartirse con otro animal: dar de baja al paciente implica eliminar su historial. |
| HistorialClinico y Consulta | Los registros de atención médica viven únicamente dentro del expediente clínico donde fueron anotados. Borrar el historial arrastra consigo todas sus consultas. |

### Agregación

| Relación | Justificación |
|---|---|
| Clinica y Veterinario | Los médicos se crean fuera de la clínica y luego se integran a la plantilla. Si el negocio cerrara mañana, cada veterinario seguiría existiendo como profesionista y podría contratarse en otro lugar; por esa independencia de vida no hay composición. |
| Clinica y Recepcionista | Ocurre el mismo caso de ciclo de vida independiente: el personal de recepción se instancia por separado y el sistema únicamente lo vincula mientras trabaja ahí. |

### Asociación

| Relación | Justificación |
|---|---|
| Clinica con Dueno, Mascota, Cita y Comprobante | Aquí la clínica funciona como punto de acceso para búsquedas y registros mediante listas de referencias, sin ser dueña de la existencia de esos objetos. |
| Mascota hacia Dueno (propietario) | Vínculo directo para saber a quién pertenece cada animal. El cliente sigue registrado aunque su mascota ya no esté en el sistema. |
| Cita hacia Mascota (paciente), Veterinario (medicoAsignado) y Consultorio (espacioAsignado) | Una cita únicamente cruza referencias de tres entidades previamente dadas de alta para apartar una fecha y hora. Cancelar o borrar la cita deja intactos al animal, al doctor y al cuarto. |
| Comprobante hacia Cita (citaFacturada) | Apunta a la cita que originó el cobro para poder armar el recibo, sin que la cita dependa del comprobante para existir. |
| Cita hacia EstadoCita | Atributo que toma una constante del enumerado para indicar en qué etapa va la cita. |
| Consulta hacia Veterinario | Guarda la referencia del médico que revisó al paciente en esa visita puntual. La vida del objeto veterinario es ajena a la consulta. |
| Consulta y Comprobante hacia ServicioCobrar | Ambos mantienen listas de servicios aplicados o cobrados para sumar sus tarifas; una misma instancia de servicio puede referenciarse desde ambos lados sin relación de pertenencia. |
