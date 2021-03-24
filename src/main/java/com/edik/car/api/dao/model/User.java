package com.edik.car.api.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Accessors(chain = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(
        fetch = FetchType.LAZY,
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @PrimaryKeyJoinColumn
    private UserInformation userInformation;

    @Default
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Ad> ads = new ArrayList<>();

    @Default
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<UserPhone> userPhones = new ArrayList<>();

    public void addUserPhone(UserPhone userPhone) {
        userPhones.add(userPhone);
        userPhone.setUser(this);
    }

    public void removeUserPhone(UserPhone userPhone) {
        userPhones.remove(userPhone);
        userPhone.setUser(null);
    }

    public void addAd(Ad ad) {
        ads.add(ad);
        ad.setUser(this);
    }

    public void removeAd(Ad ad) {
        ads.remove(ad);
        ad.setUser(null);
    }

    public void addUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
        userInformation.setUserId(userId);
        userInformation.setUser(this);
    }

    public void removeUserInformation(UserInformation userInformation) {
        this.userInformation = null;
        userInformation.setUserId(null);
        userInformation.setUser(null);
    }
}
