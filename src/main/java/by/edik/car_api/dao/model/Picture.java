package by.edik.car_api.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Accessors(chain = true)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue
    @Column(name = "picture_id", nullable = false)
    private Long pictureId;

    @Column(nullable = false)
    private String reference;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
}
