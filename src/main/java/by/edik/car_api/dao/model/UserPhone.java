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
import javax.persistence.MapsId;
import javax.persistence.Table;

@Accessors(chain = true)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_phones")
public class UserPhone {
    @Id
    @GeneratedValue
    @Column(name = "phone_number_id", nullable = false)
    private Long phoneNumberId;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
