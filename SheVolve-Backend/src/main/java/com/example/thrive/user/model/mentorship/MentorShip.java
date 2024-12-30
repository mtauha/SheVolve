package com.example.thrive.user.model.mentorship;

import com.example.thrive.user.model.EntrepreneurModel;
import com.example.thrive.user.model.MentorModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "mentorship")
public class MentorShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mentorshipId;

    @NotNull(message = "mentor can not be null")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id", updatable = false)
    private MentorModel mentor;

    @NotNull(message = "mentee can not be null")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entrepreneur_id", updatable = false)
    private EntrepreneurModel mentee;

    @NotNull(message = "status can not be null")
    @Enumerated(EnumType.STRING)
    private MentorShipStatus status;

    @Column(updatable = false)
    private Date startDate;

    @NotNull(message = "end date must be specified")
    @Column(updatable = false)
    private Date endDate;

    @PrePersist
    private void onCreate() {
        this.startDate = new Date(System.currentTimeMillis());
        status = MentorShipStatus.ACTIVE;
        if (endDate.before(startDate))
            throw new RuntimeException("End date cannot be before start date");
    }

    public MentorShipStatus getStatus() {
        if (this.endDate != null && this.endDate.before(new Date())) {
            status = MentorShipStatus.FINISHED;
        }
        return this.status;
    }

}