package ar.edu.itba.paw.shared;

public enum Result {
    OK(""),

    COURSE_EXISTS_GRADE("Existen alumnos que poseen notas de este curso."),
    COURSE_EXISTS_INSCRIPTION("Existen alumnos que están inscriptos en este curso."),
    COURSE_EXISTS_ID("Ya existe un curso con este ID."),

    STUDENT_EXISTS_DOCKET("Ya existe un alumno con este legajo."),
    STUDENT_EXISTS_DNI("Ya existe un alumno con este DNI."),
    STUDENT_NOT_EXISTS("El alumno especificado no existe."),
    GRADE_EXISTS("La nota ya existe"),  /* +++xremove when adding date to grade DB */
    COURSE_NOT_EXISTS("La materia no existe"),
    INSCRIPTION_NOT_EXISTS("El estudiante no está inscripto en esta materia"),
    INVALID_INPUT_PARAMETERS("Alguno de los datos necesarios para realizar la acción es inválido"),
    ERROR_UNKNOWN("Un error desconocido ha ocurrido"),
    ERROR_ID_OUT_OF_BOUNDS("El ID está fuera de los límites permitidos"),
    ERROR_DOCKET_OUT_OF_BOUNDS("El legajo está fuera de los límites permitidos"),
	ALREADY_ENROLLED("El alumno ya está inscripto en esa materia."),
	NOT_EXISTENT_ENROLL("El alumno no estaba inscripto en esa materia");


    private final String message;

    Result(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}