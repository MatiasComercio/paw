package ar.edu.itba.paw.webapp.forms;

public class InscriptionForm {
	private int studentDocket;
	private int courseId;

	public int getStudentDocket() {
		return studentDocket;
	}

	public void setStudentDocket(final int studentDocket) {
		this.studentDocket = studentDocket;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(final int courseId) {
		this.courseId = courseId;
	}
}
