package com.petstylelab.groomersunite.domain.rating;

import com.petstylelab.groomersunite.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rating extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal completeness; // 완성도

    private BigDecimal finish; // 면처리

    private BigDecimal symmetry; // 대칭

    private BigDecimal balance; // 밸런스
}