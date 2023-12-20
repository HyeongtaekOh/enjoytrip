package com.ssafy.enjoytrip.comment.domain;

import com.ssafy.enjoytrip.member.domain.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "content_id", nullable = false)
    private Integer contentId;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime;

    @UpdateTimestamp
    @Column(name = "modified_time")
    private Instant modifiedTime;
}
