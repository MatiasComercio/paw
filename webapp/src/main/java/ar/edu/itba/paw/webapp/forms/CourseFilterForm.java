package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Length;

public class CourseFilterForm {
	private Integer id;

	@Length(min = 2)
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
