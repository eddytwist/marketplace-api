package by.edik.car_api.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue
    @Column(name = "ad_id", nullable = false)
    private Long adId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "engine_volume", nullable = false)
    private Integer engineVolume;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;

    private Long mileage;

    @Column(name = "engine_power")
    private Integer enginePower;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "editing_time", nullable = false)
    private LocalDateTime editingTime;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictures;
}
