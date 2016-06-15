package ar.edu.itba.paw.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TranscriptGrade {
	private Integer id;
	private Integer docket;
	private Integer courseId;
	private String courseName;
	private BigDecimal grade;
	private LocalDateTime modified;
	private boolean taking;
	private boolean canEdit = false;
	private List<FinalGrade> finalGrades;
	private String courseCodId;

	public void loadFromGrade(final Grade grade){
		this.id = grade.getId();
		this.docket = grade.getStudentDocket();
		this.courseId = grade.getCourseId();
		this.courseCodId = grade.getCourseCodId();
		this.courseName = grade.getCourseName();
		this.grade = grade.getGrade();
		this.modified = grade.getModified();
		this.finalGrades = grade.getFinalGrades();
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

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(final boolean canEdit) {
		this.canEdit = canEdit;
	}

	public List<FinalGrade> getFinalGrades() {
		return finalGrades;
	}

	public void setFinalGrades(List<FinalGrade> finalGrades) {
		this.finalGrades = finalGrades;
	}

	public String getCourseCodId() {
		return courseCodId;
	}

	public void setCourseCodId(final String courseCodId) {
		this.courseCodId = courseCodId;
	}
}
