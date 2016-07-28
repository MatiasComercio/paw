package ar.edu.itba.paw.models;


import ar.edu.itba.paw.models.FinalInscription.FinalInscriptionState;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "finalinstance")
public class FinalInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finalInstance_finalInstanceid_seq")
    @SequenceGenerator(sequenceName = "finalInstance_finalInstanceid_seq", name = "finalInstance_finalInstanceid_seq", allocationSize = 1)
    private int id;

    @Basic
    @Column(nullable = false, length = 50)
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    private List<FinalInscription> finalInscriptions;

    @Enumerated(value = EnumType.STRING)
    private FinalInscriptionState state = FinalInscriptionState.OPEN;

    //public List<FinalInscription> getFinalInscriptions() {
    //    return finalInscriptions;
    //}

    //public void setFinalInscriptions(List<FinalInscription> finalInscriptions) {
    //    this.finalInscriptions = finalInscriptions;
    //}

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public FinalInscriptionState getState() {
        return state;
    }

    public void setState(FinalInscriptionState state) {
        this.state = state;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
