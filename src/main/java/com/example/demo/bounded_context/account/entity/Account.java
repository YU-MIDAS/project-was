package com.example.demo.bounded_context.account.entity;


import com.example.demo.base.common.BaseTimeEntity;
import com.example.demo.bounded_context.account.dto.AccountUpdateRequest;
import com.example.demo.bounded_context.solution.entity.Waste;
import com.example.demo.bounded_context.wiki.entity.Wiki;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {
    @Column(nullable = false, unique = true)
    private String accountName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private Role role;

    private String nickname;

    private Double latitude;

    private Double longitude;

    @OneToMany(mappedBy = "writer")
    private List<Waste> waste = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Wiki> wikis = new ArrayList<>();

    @Builder
    public Account(String accountName, String password, String email, String nickname, Double latitude, Double longitude) {
        this.accountName = accountName;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.role = Role.USER;
    }

    public void update(AccountUpdateRequest request){
        this.email = request.email();
        this.nickname = request.nickname();
        this.latitude = request.latitude();
        this.longitude = request.longitude();
    }
}

