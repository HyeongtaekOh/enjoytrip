package com.ssafy.enjoytrip.attraction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "attraction_description")
@Data
public class AttractionDescription {

    @Id
    @Column(name = "content_id")
    private int contentId;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "overview")
    private String overview;

    @Column(name = "telname")
    private String telname;
}
