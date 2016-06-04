package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Procedure;
import ar.edu.itba.paw.models.users.User;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ProcedureForm {
    private Integer procedureId;

    @NotBlank
    @Min(1)
    private int senderId;

    @NotNull
    @Size(min=1, max=255)
    private String message;

    @Basic
    @Column(name = "receptor_id")
    private Integer receptorId;

    @Size(min=1, max=255)
    private String response;

    @Size(min=1)
    private String state;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    private User sender;

    public Procedure build() {
        return new Procedure.Builder()
                .sender(sender)
                .message(message)
                .receptorId(receptorId)
                .response(response)
                .procedureState(state)
                .date(date)
                .build();
    }

    public void loadFromProcedure(final Procedure procedure) {
        this.senderId = procedure.getSenderId();
        this.message = procedure.getMessage();
        this.receptorId = procedure.getReceptorId();
        this.response = procedure.getResponse();
        this.date = procedure.getDate();
        this.sender = procedure.getSender();
    }

    public Integer getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getReceptorId() {
        return receptorId;
    }

    public void setReceptorId(Integer receptorId) {
        this.receptorId = receptorId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
