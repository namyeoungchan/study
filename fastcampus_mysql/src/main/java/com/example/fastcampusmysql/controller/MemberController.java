package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entiry.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    final private MemberWriteService memberWriteService;
    final private MemberReadService memberReadService;

    @PostMapping("/members")
    public MemberDto register(@RequestBody RegisterMemberCommand command){
        var member = memberWriteService.register(command);
        return memberReadService.toDto(member);
    }

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable Long id){
        return memberReadService.getMember(id);
    }

    @PostMapping("/{id}/name")
    public MemberDto chageNickname(@PathVariable Long id , @RequestBody String nickname){
        memberWriteService.chageNickname(id,nickname);
        return memberReadService.getMember(id);
    }

    @GetMapping("/{id}")
    public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable Long memberId){
        return memberReadService.getNicknameHistories(memberId);
    }

}
