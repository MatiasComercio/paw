package ar.edu.itba.paw.webapp.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class QualifyStudent {
	@NotNull
	@Min(0)
	private Integer docket;

	@NotNull
	@Min(0)
	@Max(10)
	private BigDecimal grade;

	public QualifyStudent() {
	}

	public int getDocket() {
		return docket;
	}

	public void setDocket(int docket) {
		this.docket = docket;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(BigDecimal grade) {
		this.grade = grade;
	}
}
