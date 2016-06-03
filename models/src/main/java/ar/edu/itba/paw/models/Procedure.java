package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "procedure")
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedure_procedureid_seq")
    @SequenceGenerator(sequenceName = "procedure_procedureid_seq", name = "procedure_procedureid_seq", allocationSize = 1)
    private int id;

    @Column(name = "sender_id", nullable = false, updatable = false)
    private int senderId;
//    @ManyToOne
//    private User senderId;

    @Basic
    @Column(name = "message", nullable = false, length = 255)
    private String message;

    @Basic
    @Column(name = "receptor_id")
    private Integer receptorId;

    @Basic
    @Column(name = "response", length = 255)
    private String response;

    @Enumerated(EnumType.STRING)
    private ProcedureState state;

    @Basic
    @Column(name = "date", updatable = false)
    private LocalDateTime date;

    // Constructors //

    private Procedure(final Builder builder) {
        this.senderId = builder.senderId;
        this.message = builder.message;
        this.receptorId = builder.receptorId;
        this.response = builder.response;
        this.state = builder.state;
        this.date = builder.date;
    }

    protected Procedure() {
    }

    // Getters //

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getResponse() {
        return response;
    }

    public int getSenderId() {
        return senderId;
    }

    public Integer getReceptorId() {
        return receptorId;
    }

    public ProcedureState getState() {
        return state;
    }

    public LocalDateTime getDate() {
        return date;
    }

    // Setters //

    public void setState(final ProcedureState state) {
        this.state = state;
    }

    // Equals and Hashcode //


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Procedure procedure = (Procedure) o;

        if (id != procedure.id) return false;
        if (senderId != procedure.senderId) return false;
        if (!message.equals(procedure.message)) return false;
        if (receptorId != null ? !receptorId.equals(procedure.receptorId) : procedure.receptorId != null) return false;
        if (response != null ? !response.equals(procedure.response) : procedure.response != null) return false;
        if (state != procedure.state) return false;
        return date.equals(procedure.date);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + senderId;
        result = 31 * result + message.hashCode();
        result = 31 * result + (receptorId != null ? receptorId.hashCode() : 0);
        result = 31 * result + (response != null ? response.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", message='" + message + '\'' +
                ", receptorId=" + receptorId +
                ", response='" + response + '\'' +
                ", state=" + state +
                ", date=" + date +
                '}';
    }

    public static class Builder {
        private int senderId;
        private String message;
        private Integer receptorId;
        private String response;
        private ProcedureState state;
        private LocalDateTime date;

        public Builder() {
            this.date = LocalDateTime.now();
        }

        public Builder senderId(final int senderId) {
            this.senderId = senderId;
            return this;
        }

        public Builder message(final String message) {
            this.message = message;
            return this;
        }
        public Builder receptorId(final int receptorId) {
            this.receptorId = receptorId;
            return this;
        }
        public Builder response(final String response) {
            this.response = response;
            return this;
        }

        public Builder procedureState(final ProcedureState state) {
            this.state = state;
            return this;
        }

        public Builder date(final LocalDateTime date) {
            if (date != null) {
                this.date = date;
            }
            return this;
        }

        public Procedure build() {
            return new Procedure(this);
        }
    }
}
