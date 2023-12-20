package com.ssafy.enjoytrip.plan.domain;


import com.ssafy.enjoytrip.attraction.domain.AttractionInfo;
import com.ssafy.enjoytrip.plan.domain.id.PlanAttractionId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "attraction_plan")
public class PlanAttractionSequence {

    @EmbeddedId
    private PlanAttractionId planAttractionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("planId")
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contentId")
    @JoinColumn(name = "content_id")
    private AttractionInfo attractionInfo;

    @Column(name = "plan_order", nullable = false)
    private Integer planOrder;
}
