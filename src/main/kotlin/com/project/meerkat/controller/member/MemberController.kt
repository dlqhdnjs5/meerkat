package com.project.meerkat.controller.member

import com.project.meerkat.common.model.UserInfo
import com.project.meerkat.model.member.AddMemberRequest
import com.project.meerkat.model.member.LoginMemberRequest
import com.project.meerkat.service.member.MemberSerivce
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

    @PostMapping("/send/address-check/mail")
    fun sendAddressCheckMail(@RequestBody email: String) {
        memberSerivce.sendAddressCheckMail(email)
    }

    @PutMapping("/notification-email/{notificationEmail}")
    fun updateNotificationEmail(@PathVariable("notificationEmail") notificationEmail: String): UserInfo {
        return memberSerivce.updateMemberNotificationEmail(notificationEmail)
    }
}