package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InscriptionForm {

	@NotNull
	private Integer studentDocket;

	@Min(1)
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
