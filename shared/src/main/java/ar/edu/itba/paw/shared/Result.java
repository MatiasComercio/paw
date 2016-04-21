package ar.edu.itba.paw.shared;

public enum Result {
	OK(""),
	COURSE_EXISTS_GRADE("Existen alumnos que poseen notas de este curso."),
	COURSE_EXISTS_INSCRIPTION("Existen alumnos que están inscriptos en este curso."),
	ERROR_UNKNOWN("Un error desconocido ha ocurrido"),
	ERROR_ID_OUT_OF_BOUNDS("El ID está fuera de los límites permitidos"),
	ERROR_DOCKET_OUT_OF_BOUNDS("El legajo está fuera de los límites permitidos"),
	COURSE_EXISTS_ID("Ya existe curso con este ID"),
	STUDENT_EXISTS_DOCKET("Ya existia un alumno con este legajo"),
	ALREADY_ENROLLED("El alumno ya está inscripto en esa materia.");

	private final String message;

	Result(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
