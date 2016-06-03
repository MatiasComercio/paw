package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Procedure;
import ar.edu.itba.paw.models.ProcedureState;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ProcedureForm {
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

    /**
     * Enumerated is from javax.persistence
     * so i'm not sure if this validation will work
     */
    @Enumerated(EnumType.STRING)
    private ProcedureState state;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    public Procedure build() {
        return new Procedure.Builder()
                .senderId(senderId)
                .message(message)
                .receptorId(receptorId)
                .response(response)
                .procedureState(state)
                .date(date)
                .build();
    }


}
