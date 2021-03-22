package com.edik.car.api.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue
    @Column(name = "ad_id")
    private Long adId;

    @Column()
    private Integer year;

    @Column()
    private String brand;

    @Column()
    private String model;

    @Column(name = "engine_volume")
    private Integer engineVolume;

    @Enumerated(EnumType.STRING)
    @Column()
    private Condition condition;

    private Long mileage;

    @Column(name = "engine_power")
    private Integer enginePower;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "editing_time")
    private LocalDateTime editingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Default
    @OneToMany(
        mappedBy = "ad",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
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
