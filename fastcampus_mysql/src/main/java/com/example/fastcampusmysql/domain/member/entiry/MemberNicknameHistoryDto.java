package com.example.fastcampusmysql.domain.member.entiry;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
public class MemberNicknameHistoryDto {
    final private Long id;
    final private Long memberId;
    final private String nickname;
    final private LocalDateTime createdAt;
    @Builder
    public MemberNicknameHistoryDto(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;;
    }
}
