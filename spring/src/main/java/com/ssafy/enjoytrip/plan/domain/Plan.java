package com.ssafy.enjoytrip.plan.domain;

import com.ssafy.enjoytrip.attraction.domain.AttractionInfo;
import com.ssafy.enjoytrip.member.domain.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "theme", nullable = false, length = 50)
    private String theme;

    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime;

    @UpdateTimestamp
    @Column(name = "modified_time")
    private Instant modifiedTime;

    @Column(name = "hit")
    private Long hit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "departures_id", nullable = false)
    private AttractionInfo departures;

    @ManyToOne
    @JoinColumn(name = "arrivals_id", nullable = false)
    private AttractionInfo arrivals;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("planOrder ASC")
    private List<PlanAttractionSequence> planAttractionSequences = new ArrayList<>();
}
