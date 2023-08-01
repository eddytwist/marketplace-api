package com.edik.car.api.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ads")
@Accessors(chain = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id", nullable = false)
    private Long adId;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "engine_volume")
    private Integer engineVolume;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;

    private Long mileage;

    @Column(name = "engine_power")
    private Integer enginePower;

    @CreationTimestamp
    @Column(
        name = "creation_time",
        updatable = false,
        nullable = true
    )
    private LocalDateTime creationTime;

    @Column(
        name = "editing_time",
        insertable = false,
        nullable = true
    )
    private LocalDateTime editingTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Default
    @OneToMany(
        mappedBy = "ad",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @Fetch(FetchMode.JOIN)
    private List<Picture> pictures = new ArrayList<>();

    public void addPicture(Picture picture) {
        pictures.add(picture);
        picture.setAd(this);
    }

    public void removePicture(Picture picture) {
        pictures.remove(picture);
        picture.setAd(null);
    }
}
