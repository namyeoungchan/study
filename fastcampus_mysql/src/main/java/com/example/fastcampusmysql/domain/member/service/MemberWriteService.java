package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entiry.Member;
import com.example.fastcampusmysql.domain.member.entiry.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {
    final private MemberRepository memberRepository;
    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;
    public Member register(RegisterMemberCommand command) {
        /*
        * 목표 - 회원 정보 (이메일 , 닉네임 , 생년월일)을 등록한다,
        *     - 닉네임 10자를 넘길수 없다.
        * 파라미터 - memberRegisterCommand
        * val member = Member.of(memberRegisterCommand)
        * memberRepository.save(member)
        * */
    var member = Member.builder()
            .nickname(command.nickname())
            .email(command.email())
            .birthday(command.birthday())
            .build();
    var savedMember = memberRepository.save(member);
    saveNicknameHistory(savedMember);
    return savedMember;
    }
    public void chageNickname(Long memberid , String nickname){
        var member = memberRepository.findById(memberid).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);
        saveNicknameHistory(member);

        //todd
    }
    private void saveNicknameHistory(Member member){
        var history = MemberNicknameHistoryDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
        memberNicknameHistoryRepository.save(history);
    }
}
