package ar.edu.itba.paw.webapp.models;

import java.util.List;

public class QualifiedStudentsList {
	private List<QualifyStudent> studentsWithGrade;

	public QualifiedStudentsList() {
	}

	public List<QualifyStudent> getStudentsWithGrade() {
		return studentsWithGrade;
	}

	public int size() {
		if (studentsWithGrade == null) {
			return 0;
		}
		return studentsWithGrade.size();
	}
}
