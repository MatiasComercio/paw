package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.users.Student;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "grade")
//@IdClass(GradePK.class) //TODO: DELETE
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_gradeid_seq")
	@SequenceGenerator(sequenceName = "grade_gradeid_seq", name = "grade_gradeid_seq", allocationSize = 1)
	private int id;

	@Column(name = "docket", nullable = false)
	private int studentDocket;

	@Transient
	private String studentFirstName;

	@Transient
	private String studentLastName;

	@Column(name = "course_id", nullable = false)
	private int courseId;

	@Transient
	private String courseName;

	@Column(name = "grade", nullable = false, precision = 2)
	private BigDecimal grade;

	@Column(name = "modified", nullable = false)
	private LocalDateTime modified;

	@Transient
    private Boolean taking;

	// +++xcheck
//	@ManyToOne
//	@JoinColumn(name = "docket", referencedColumnName = "docket", nullable = false)
//	private Student studentByDocket;

//	@ManyToOne
//	@JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
//	private Course courseByCourseId;

	private Grade(final Builder builder) {
		this.studentDocket = builder.studentDocket;
		this.studentFirstName = builder.studentFirstName;
		this.studentLastName = builder.studentLastName;
		this.courseId = builder.courseId;
		this.courseName = builder.courseName;
		this.grade = builder.grade;
		this.modified = builder.modified;
        this.taking = builder.taking;
	}

	protected Grade() {
		// Just for Hibernate
	}

	public int getStudentDocket() {
		return studentDocket;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public LocalDateTime getModified() { return modified; }

    public Boolean getTaking() {
        return taking;
    }


	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Grade)) return false;

		final Grade grade1 = (Grade) o;

		if (studentDocket != grade1.studentDocket) return false;
		if (courseId != grade1.courseId) return false;
		if (!grade.equals(grade1.grade)) return false;
		return modified.equals(grade1.modified);

	}

	@Override
	public int hashCode() {
		int result = studentDocket;
		result = 31 * result + courseId;

        //TODO: grade and modified could be null
        if (grade != null)
            result = 31 * result + grade.hashCode();
		if (modified != null)
            result = 31 * result + modified.hashCode();
		return result;
	}

	public static class Builder {
		private final int studentDocket;
		private final int courseId;
		private final BigDecimal grade;

		private String studentFirstName = "";
		private String studentLastName = "";
		private String courseName = "";
		private LocalDateTime modified;
        private Boolean taking = false;

		public Builder(final int studentDocket, final int courseId, final BigDecimal grade) {
			this.studentDocket = studentDocket;
			this.courseId = courseId;
			this.grade = grade;
		}

		public Builder studentFirstName(final String studentFirstName) {
			this.studentFirstName = studentFirstName;
			return this;
		}

		public Builder studentLastName(final String studentLastName) {
			this.studentLastName = studentLastName;
			return this;
		}

		public Builder courseName(final String courseName) {
			this.courseName = courseName;
			return this;
		}

		public Builder modified(final LocalDateTime modified) {
			this.modified = modified;
			return this;
		}

        public Builder taking(final Boolean taking){
            this.taking = taking;
            return this;
        }

		public Grade build() {
			return new Grade(this);
		}
	}
}
