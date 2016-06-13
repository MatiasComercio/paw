package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Size;

public class CourseFilterForm {
	@Size(max=5)
	private String id;

	@Size(max=50)
	private String name;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name.trim();
	}
}
