package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "finalgrade")
public class FinalGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finalgrade_finalgradeid_seq")
    @SequenceGenerator(sequenceName = "finalgrade_finalgradeid_seq", name = "finalgrade_finalgradeid_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "grade", nullable = false, precision = 2)
    private BigDecimal grade;

    @Column(name = "modified", nullable = false)
    private LocalDateTime modified;


    protected FinalGrade(){
        //Just for hibernate
    }

    private FinalGrade(Builder builder){
        this.id = builder.id;
        this.grade = builder.grade;
        this.modified = builder.modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalGrade that = (FinalGrade) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        return modified != null ? modified.equals(that.modified) : that.modified == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FinalGrade{" +
                "id=" + id +
                ", grade=" + grade +
                ", modified=" + modified +
                '}';
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class Builder {
        private final Integer id;
        private final BigDecimal grade;
        private LocalDateTime modified;

        public Builder(final Integer id, final BigDecimal grade) {
            this.id = id;
            this.grade = grade;
            this.modified = LocalDateTime.now();
        }

        public Builder modified(final LocalDateTime modified) {
            if (modified != null) {
                this.modified = modified;
            }
            return this;
        }

        public FinalGrade build() {
            return new FinalGrade(this);
        }
    }

}
