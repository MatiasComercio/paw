package ar.edu.itba.paw.webapp.models;

import java.util.List;


public class StudentsWithGradeList {

	private List<StudentWithGradeDTO> students;

	public StudentsWithGradeList() {
	}

	public StudentsWithGradeList(final List<StudentWithGradeDTO> studentWithGradeDTOS) {
		this.students = studentWithGradeDTOS;
	}

	public List<StudentWithGradeDTO> getStudents() {
		return students;
	}

	public void setStudents(final List<StudentWithGradeDTO> students) {
		this.students = students;
	}
}
