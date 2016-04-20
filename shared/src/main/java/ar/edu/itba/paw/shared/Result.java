package ar.edu.itba.paw.shared;

public enum Result {
    OK(""),
    COURSE_EXISTS_GRADE("Existen alumnos que poseen notas de este curso."),
    COURSE_EXISTS_INSCRIPTION("Existen alumnos que poseen están inscriptos en este curso.");

    private final String message;

    Result(String message) {
        this.message = message;
    }
}
