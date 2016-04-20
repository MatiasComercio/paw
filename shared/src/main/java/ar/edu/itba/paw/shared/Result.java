package ar.edu.itba.paw.shared;

public enum Result {
    OK(""),
    COURSE_EXISTS_GRADE("Existen alumnos que poseen notas de este curso."),
    COURSE_EXISTS_INSCRIPTION("Existen alumnos que poseen están inscriptos en este curso."),
    ERROR_UNKNOWN("Un error desconocido ha ocurrido"),
    ERROR_ID_OUT_OF_BOUNDS("El ID está fuera de los límites permitidos");

    private final String message;

    Result(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
