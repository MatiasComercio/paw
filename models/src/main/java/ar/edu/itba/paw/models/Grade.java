package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "grade")
public class Grade {

	@ManyToOne
	@JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
	private Course course;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_gradeid_seq")
	@SequenceGenerator(sequenceName = "grade_gradeid_seq", name = "grade_gradeid_seq", allocationSize = 1)
	private Integer id;

	@Column(name = "docket", nullable = false)
	private int studentDocket;

	@Transient
	private String studentFirstName;

	@Transient
	private String studentLastName;

	@Column(name = "grade", nullable = false, precision = 2)
	private BigDecimal grade;

	@Column(name = "modified", nullable = false)
	private LocalDateTime modified;

	@Transient
    private Boolean taking;

    @OneToMany()
	private List<FinalGrade> finalGrades;

	public void setTaking(final Boolean taking) {
		this.taking = taking;
	}

	// +++xcheck
//	@ManyToOne
//	@JoinColumn(name = "docket", referencedColumnName = "docket", nullable = false)
//	private Student studentByDocket;

	private Grade(final Builder builder) {
		this.id = builder.id;
		this.studentDocket = builder.studentDocket;
		this.studentFirstName = builder.studentFirstName;
		this.studentLastName = builder.studentLastName;
		this.course = new Course.Builder(builder.courseId).name(builder.courseName).build();
		this.grade = builder.grade;
		this.modified = builder.modified;
        this.taking = builder.taking;
	}

	protected Grade() {
		// Just for Hibernate
	}

	public Integer getId() {
		return id;
	}

	public Course getCourse() {
		return course;
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
		return course.getId();
	}

	public String getCourseName() {
		return course.getName();
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public LocalDateTime getModified() { return modified; }


    public List<FinalGrade> getFinalGrades() {
        return finalGrades;
    }

    public void setFinalGrades(List<FinalGrade> finalGrades) {
        this.finalGrades = finalGrades;
    }

    public Boolean getTaking() {
        return taking;
    }


	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Grade)) return false;

		final Grade grade1 = (Grade) o;

		if (id != grade1.id) return false;
		if (studentDocket != grade1.studentDocket) return false;
		if (course != null ? !course.equals(grade1.course) : grade1.course != null) return false;
		if (grade != null ? !grade.equals(grade1.grade) : grade1.grade != null) return false;
		return modified != null ? modified.equals(grade1.modified) : grade1.modified == null;

	}

	@Override
	public int hashCode() {
		int result = course != null ? course.hashCode() : 0;
		result = 31 * result + id;
		result = 31 * result + studentDocket;
		result = 31 * result + (grade != null ? grade.hashCode() : 0);
		result = 31 * result + (modified != null ? modified.hashCode() : 0);
		return result;
	}

	public static class Builder {
		private final Integer id;
		private final int studentDocket;
		private final int courseId;
		private final BigDecimal grade;

		private String studentFirstName = "";
		private String studentLastName = "";
		private String courseName = "";
		private LocalDateTime modified;
        private Boolean taking = false;

		public Builder(final Integer id, final int studentDocket, final int courseId, final BigDecimal grade) {
			this.id = id;
			this.studentDocket = studentDocket;
			this.courseId = courseId;
			this.grade = grade;
			this.modified = LocalDateTime.now();
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
			if (modified != null) {
				this.modified = modified;
			}
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
