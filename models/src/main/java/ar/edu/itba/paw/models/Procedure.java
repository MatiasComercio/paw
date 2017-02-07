package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "procedure")
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedure_procedureid_seq")
    @SequenceGenerator(sequenceName = "procedure_procedureid_seq", name = "procedure_procedureid_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "title", nullable = false, length = 64)
    private String title;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "dni" /*, nullable = false makes it fail to add for constraint*/)
    private User sender;

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
        this.title = builder.title;
        this.sender = builder.sender;
        this.message = builder.message;
        this.receptorId = builder.receptorId;
        this.response = builder.response;
        this.state = builder.state;
        this.date = builder.date;
    }

    protected Procedure() {
    }

    // Getters //


    public String getTitle() {
        return title;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getResponse() {
        return response;
    }

    public User getSender() {
        return sender;
    }

    public int getSenderId() {
        return sender.getDni();
    }

    public Integer getReceptorId() {
        return receptorId;
    }

    public ProcedureState getState() {
        return state;
    }

    public void setState(final ProcedureState state) {
        this.state = state;
    }

    // Setters //

    public LocalDateTime getDate() {
        return date;
    }

    // Equals and Hashcode //

    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", sender=" + sender +
                ", message='" + message + '\'' +
                ", receptorId=" + receptorId +
                ", response='" + response + '\'' +
                ", state=" + state +
                ", date=" + date +
                '}';
    }

    public static class Builder {
        private String title;
        private User sender;
        private String message;
        private Integer receptorId;
        private String response;
        private ProcedureState state;
        private LocalDateTime date;

        public Builder() {
            this.date = LocalDateTime.now();
        }

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder sender(final User sender) {
            this.sender = sender;
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

        public Builder procedureState(final String state) {
            try {
                this.state = ProcedureState.valueOf(state);
            } catch (IllegalArgumentException e) {
                this.state = null; /* Let it explode in the persistence layer */
            }

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
