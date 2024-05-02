package com.project.meerkat.mail

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


@SpringBootTest
class SendMailTest {

    @Test
    fun sendMailTest() {
        val type = "text/html; charset=utf-8"
        val emailAdd = "meerkat.noti@gmail.com"
        val password = "lavi bijy mbfr papj"
        val to = "dlqhdnjs5@naver.com"

        val properties = Properties()
        properties["mail.smtp.host"] = "smtp.gmail.com"
        properties["mail.smtp.port"] = 587
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2")
        val auth: Authenticator = object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(emailAdd, password)
            }
        }
        val session: Session = Session.getInstance(properties, auth)
        try {
            val message: Message = MimeMessage(session)
            message.setFrom(InternetAddress(emailAdd, "발신자이름"))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(to))
            message.setSubject("meerkat test")
            message.setContent("meerkat Test content", type)
            Transport.send(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}