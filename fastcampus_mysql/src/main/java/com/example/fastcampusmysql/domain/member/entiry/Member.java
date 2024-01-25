package com.example.fastcampusmysql.domain.member.entiry;

import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@Getter
public class Member {


    final private Long id;

    private String nickname;

    final private String email;

    final private LocalDate birthday;

    final private LocalDateTime createdAt;

    final private static Long NAME_MAX_LENGTH = 10L;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthday, LocalDateTime createdAt) {
        this.id = id;
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
    }

    private void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "닉네임은 10자를 넘길수 없습니다.");
    }

    public void changeNickname(String other) {
        Objects.requireNonNull(other);
        validateNickname(other);
        this.nickname = other;
    }

}
