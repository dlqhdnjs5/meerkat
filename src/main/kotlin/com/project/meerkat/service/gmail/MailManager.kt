package com.project.meerkat.service.gmail

import com.project.meerkat.common.GlobalPropertySource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Component
class MailManager(
    val globalPropertySource: GlobalPropertySource
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(MailManager::class.java)
        const val TYPE = "text/html; charset=utf-8"
        private const val MEERKAT_EMAIL = "meerkat.noti@gmail.com"

        private val properties = Properties().apply {
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587")
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            setProperty("mail.smtp.ssl.protocols", "TLSv1.2")
        }
    }

    private fun createSession(): Session {
        val auth: Authenticator = object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(MEERKAT_EMAIL, globalPropertySource.smtpGmailAppPassword)
            }
        }
        return Session.getInstance(properties, auth)
    }

    fun send(email: String, subject: String, content: String) {
        try {
            val message: Message = MimeMessage(createSession())
            message.setFrom(InternetAddress(MEERKAT_EMAIL, "Meerkat.noti"))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(email))
            message.setSubject(subject)
            message.setContent(content, TYPE)
            Transport.send(message)
        } catch (exception: Exception) {
            logger.error("error occurred while sending email. address : ${email}", exception)
        }
    }
}