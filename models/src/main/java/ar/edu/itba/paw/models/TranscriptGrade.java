package ar.edu.itba.paw.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TranscriptGrade {
	private Integer docket;
	private Integer courseId;
	private String courseName;
	private BigDecimal grade;
	private LocalDateTime modified;
	private boolean taking;

	public void loadFromGrade(final Grade grade){
		this.docket = grade.getStudentDocket();
		this.courseId = grade.getCourseId();
		this.courseName = grade.getCourseName();
		this.grade = grade.getGrade();
		this.modified = grade.getModified();
	}

	public Integer getDocket() {
		return docket;
	}

	public void setDocket(final Integer docket) {
		this.docket = docket;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(final Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(final String courseName) {
		this.courseName = courseName;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(final BigDecimal grade) {
		this.grade = grade;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(final LocalDateTime modified) {
		this.modified = modified;
	}

	public boolean isTaking() {
		return taking;
	}

	public void setTaking(final boolean taking) {
		this.taking = taking;
	}
}
