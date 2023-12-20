package com.ssafy.enjoytrip.plan.domain.id;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode
public class PlanAttractionId implements Serializable {

    private Integer planId;
    private Integer contentId;
}
