package com.ssafy.enjoytrip.qna.domain;

import com.ssafy.enjoytrip.member.domain.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "qna_board")
public class QnaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "hits")
    private Integer hits = 0;

    @CreationTimestamp
    @Column(name= "created_time", nullable= false)
    private Instant createdTime;

    @UpdateTimestamp
    @Column(name= "modified_time", nullable= false)
    private Instant modifiedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;
}
