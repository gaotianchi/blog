package com.gaotianchi.resourceservice.entity;

import com.gaotianchi.resourceservice.enums.VoterAttitude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
public class CommentVoteEntity {
    @Id
    @GeneratedValue
    private Long id;
    private OffsetDateTime votingDatetime;
    @Enumerated(EnumType.STRING)
    private VoterAttitude voterAttitude;

    @ManyToOne
    private UserEntity voter;

    @ManyToOne
    private CommentEntity target;
}
