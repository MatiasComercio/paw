package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.users.Student;

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

	@Column(name = "grade", nullable = false, precision = 2)
	private BigDecimal grade;

	@Column(name = "modified", nullable = false)
	private LocalDateTime modified;

	@Transient
	private Boolean taking;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<FinalGrade> finalGrades;

	@ManyToOne
	@JoinColumn(name = "docket", referencedColumnName = "docket", nullable = false)
	private Student student;

	private Grade(final Builder builder) {
		this.id = builder.id;
		this.student = builder.student;
		this.course = new Course.Builder(builder.courseId)
						.name(builder.courseName)
						.courseId(builder.courseCodId)
						.build();
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
		return student.getDocket();
	}

	public String getStudentFirstName() {
		return student.getFirstName();
	}

	public String getStudentLastName() {
		return student.getLastName();
	}

	public int getCourseId() {
		return course.getId();
	}

	public String getCourseCodId() {
		return course.getCourseId();
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

	public void setTaking(final Boolean taking) {
		this.taking = taking;
	}

	public void setStudent(final Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setGrade(BigDecimal grade) {
		this.grade = grade;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Grade)) return false;

		final Grade grade1 = (Grade) o;

		if (course != null ? !course.equals(grade1.course) : grade1.course != null) return false;
		if (id != null ? !id.equals(grade1.id) : grade1.id != null) return false;
		if (grade != null ? !grade.equals(grade1.grade) : grade1.grade != null) return false;
		if (modified != null ? !modified.equals(grade1.modified) : grade1.modified != null) return false;
		return student != null ? student.equals(grade1.student) : grade1.student == null;

	}

	@Override
	public int hashCode() {
		int result = course != null ? course.hashCode() : 0;
		result = 31 * result + (id != null ? id.hashCode() : 0);
		result = 31 * result + (grade != null ? grade.hashCode() : 0);
		result = 31 * result + (modified != null ? modified.hashCode() : 0);
		result = 31 * result + (student != null ? student.hashCode() : 0);
		return result;
	}

	public void setCourse(final Course course) {
		this.course = course;
	}

	public static class Builder {
		private final Integer id;
		private final int courseId;
		private final String courseCodId;
		private final BigDecimal grade;

		private String studentFirstName = "";
		private String studentLastName = "";
		private Student student;
		private Course course;
		private String courseName = "";
		private LocalDateTime modified;
		private Boolean taking = false;

		public Builder(final Integer id, final Student student, final int courseId, final String courseCodId, final BigDecimal grade) {
			this.id = id;
			this.student = student;
			this.courseId = courseId;
			this.courseCodId = courseCodId;
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
