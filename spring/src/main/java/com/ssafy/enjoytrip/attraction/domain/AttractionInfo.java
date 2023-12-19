package com.ssafy.enjoytrip.attraction.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "attraction_info")
@Data
public class AttractionInfo {

    @Id
    @Column(name = "content_id", nullable = false)
    private Integer contentId;

    @Column(name = "content_type_id", nullable = false)
    private Integer contentTypeId;

    @Column(name = "title")
    private String title;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "tel")
    private String tel;

    @Column(name = "first_image")
    private String firstImage;

    @Column(name = "first_image2")
    private String firstImage2;

    @Column(name = "readcount")
    private Integer readcount;

    @Column(name = "sido_code")
    private Integer sidoCode;

    @Column(name = "gugun_code")
    private Integer gugunCode;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "mlevel")
    private String mlevel;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private AttractionDetail attractionDetail;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private AttractionDescription attractionDescription;
}
