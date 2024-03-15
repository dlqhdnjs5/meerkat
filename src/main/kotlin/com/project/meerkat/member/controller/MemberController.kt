package com.project.meerkat.member.controller

import com.project.meerkat.common.model.UserInfo
import com.project.meerkat.member.model.AddMemberRequest
import com.project.meerkat.member.model.LoginMemberRequest
import com.project.meerkat.member.service.MemberSerivce
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/members")
class MemberController (
    private val memberSerivce: MemberSerivce
) {
    @GetMapping("/exist")
    fun isExistMemberByEmail(@RequestParam("email") email: String): Boolean {
        return memberSerivce.isExistMember(email)
    }

    @PostMapping
    fun addMember(@Valid @NotNull @RequestBody addMemberRequest: AddMemberRequest) {
        memberSerivce.addMember(addMemberRequest)
    }

    @PostMapping("/login")
    fun login(@Valid @NotNull @RequestBody loginMemberRequest: LoginMemberRequest): UserInfo {
        return memberSerivce.login(loginMemberRequest)
    }
}