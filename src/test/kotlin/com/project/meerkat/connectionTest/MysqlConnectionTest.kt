package com.project.meerkat.connectionTest

import org.junit.jupiter.api.Test
import java.sql.Connection
import java.sql.DriverManager


class MysqlConnectionTest {
    private val DRIVER = "com.mysql.cj.jdbc.Driver"

    private val URL = "jdbc:mysql://127.0.0.1:5000/meerkat?characterEncoding=utf8"
    private val USER = "meerkat"
    private val PASSWORD = "meerkat123"

    @Test
    fun testConnection() {
        Class.forName(DRIVER)
        try {
            val connection: Connection = DriverManager.getConnection(URL, USER, PASSWORD)
            System.out.println(connection)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}