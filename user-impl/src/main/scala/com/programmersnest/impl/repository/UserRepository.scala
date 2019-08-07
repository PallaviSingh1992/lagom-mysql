package com.programmersnest.impl.repository

import java.sql.ResultSet

import akka.Done
import com.lightbend.lagom.scaladsl.api.transport.{ExceptionMessage, NotAcceptable, TransportErrorCode}
import com.lightbend.lagom.scaladsl.persistence.jdbc.JdbcSession
import com.programmersnest.api.models.User

import scala.concurrent.Future

class UserRepository(session: JdbcSession) {

  import JdbcSession.tryWith

  def addNewUser(user: User): Future[Done] = {
    val sql =
      """
        |INSERT INTO users (id, name, mobile_1, company, vat_no, pin_no)
        |VALUES(?, ?, ?, ?, ?, ?)
      """.stripMargin

    session.withConnection(con => {
      tryWith(con.prepareStatement(sql)) { statement => {
        statement.setString(1, user.id.get)
        statement.setString(2, user.name)
        statement.setString(3, user.mobile1)
        statement.setString(4, user.company)
        statement.setString(5, user.vatNo)
        statement.setString(6, user.pinNo)
        if (!statement.execute()) Done else throw
          new NotAcceptable(TransportErrorCode.UnsupportedData,
            new ExceptionMessage("Invalid Data", "User properties invalid"))
      }
      }
    })
  }

  def getUsers(): Future[List[User]] = {
    val sql = """SELECT * FROM users"""

    session.withConnection(con => {
      tryWith(con.prepareStatement(sql)) { stmt =>
        val result = stmt.executeQuery()

        Iterator.from(0).takeWhile(_ => result.next())
          .map(_ => User(
            Some(result.getString("id")),
            None,
            result.getString("name"),
            result.getString("mobile_1"),
            None,
            result.getString("company"),
            result.getString("vat_no"),
            None,
            None,
            None,
            None,
            None,
            result.getString("pin_no")))
          .toList
      }
    })
  }
}
