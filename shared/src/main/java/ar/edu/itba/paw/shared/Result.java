package ar.edu.itba.paw.shared;

public enum Result {
    OK(""),

    COURSE_EXISTS_GRADE("Existen alumnos que poseen notas de este curso."),
    COURSE_EXISTS_INSCRIPTION("Existen alumnos que están inscriptos en este curso."),
    COURSE_EXISTS_ID("Ya existe un curso con este ID."),
    COURSE_ALREADY_PASSED("El alumno ya ha aprobado este curso"),

    USER_NOT_EXISTS("El usuario especificado no existe"),

    STUDENT_EXISTS_DOCKET("Ya existe un alumno con este legajo."),
    STUDENT_EXISTS_DNI("Ya existe un alumno con este DNI."),
    STUDENT_NOT_EXISTS("El alumno especificado no existe."),
    GRADE_EXISTS("La nota ya existe"),  /* +++xremove when adding date to grade DB */
    COURSE_NOT_EXISTS("La materia no existe"),
    INSCRIPTION_NOT_EXISTS("El estudiante no está inscripto en esta materia"),
    INVALID_INPUT_PARAMETERS("Alguno de los datos necesarios para realizar la acción es inválido"),
    ERROR_UNKNOWN("Un error desconocido ha ocurrido"),
    ERROR_ID_OUT_OF_BOUNDS("El ID está fuera de los límites permitidos"),
    ERROR_DNI_OUT_OF_BOUNDS("El DNI está fuera de los límites permitidos"),
    ERROR_DOCKET_OUT_OF_BOUNDS("El legajo está fuera de los límites permitidos"),
    ERROR_CORRELATIVE_NOT_APPROVED("El alumno no aprobó todas las correlativas necesarias."),
	ALREADY_ENROLLED("El alumno ya está inscripto en esa materia."),
	NOT_EXISTENT_ENROLL("El alumno no estaba inscripto en esa materia"),

    CORRELATIVE_CORRELATIVITY_EXISTS("La correlatividad ya existe."),
    CORRELATIVE_SAME_COURSE("No es posible hacer a un curso correlativo a si mismo"),
    CORRELATIVE_CORRELATIVITY_LOOP("La correlatividad generaría un ciclo en el árbol de correlatividades."),
    PASSWORDS_DO_NOT_MATCH("La contraseña y su repetición no coinciden"),
    ADMIN_EXISTS_DNI("Ya existe un administrador con este DNI"),
    ADMIN_ALREADY_ENABLED_INSCRIPTIONS("Las inscripciones ya se encuentran habilitadas"),
    USER_EXISTS_DNI("Ya existe un usuario con este DNI"),
    DNI_NOT_EXISTS("El usuario con el DNI correspondiente no existe");


    private final String message;

    Result(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
