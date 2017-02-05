package ar.edu.itba.paw.webapp.models;

import java.math.BigDecimal;

public class StudentWithGradeDTO {
	private StudentIndexDTO student;
	private BigDecimal grade;

	public StudentWithGradeDTO() {
	}

	public StudentIndexDTO getStudent() {
		return student;
	}

	public void setStudent(StudentIndexDTO student) {
		this.student = student;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(BigDecimal grade) {
		this.grade = grade;
	}
}
