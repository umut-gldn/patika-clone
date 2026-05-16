package com.umutgldn.patikaclone.rating.entity;

import com.umutgldn.patikaclone.common.entity.BaseEntity;
import com.umutgldn.patikaclone.content.entity.Content;
import com.umutgldn.patikaclone.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "ratings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "content_id"})
        }
)
public class Rating extends BaseEntity {

    @Column(nullable = false)
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;
}