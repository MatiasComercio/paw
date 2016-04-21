package ar.edu.itba.paw.webapp.forms;

public class InscriptionForm {

	private Integer studentDocket;
	private Integer courseId;

	public Integer getStudentDocket() {
		return studentDocket;
	}

	public void setStudentDocket(final int studentDocket) {
		this.studentDocket = studentDocket;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(final int courseId) {
		this.courseId = courseId;
	}
}
