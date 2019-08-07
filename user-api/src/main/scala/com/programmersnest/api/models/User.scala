package com.programmersnest.api.models

import java.sql.Timestamp

import org.joda.time.DateTime
import play.api.libs.json._


case class User(
                 id: Option[String] = None,
                 date: Option[Timestamp] = None,
                 name: String,
                 mobile1: String,
                 mobile2: Option[String] = None,
                 company: String,
                 vatNo: String,
                 status: Option[String] = None,
                 address: Option[String] = None,
                 country: Option[String] = None,
                 state: Option[String] = None,
                 city: Option[String] = None,
                 pinNo: String
               )

object User {

  import play.api.libs.json.Json._
  import play.api.libs.json._

  def timestampToDateTime(t: Timestamp): DateTime = new DateTime(t.getTime)

  def dateTimeToTimestamp(dt: DateTime): Timestamp = new Timestamp(dt.getMillis)

  implicit val timestampFormat = new Format[Timestamp] {

    def writes(t: Timestamp): JsValue = toJson(timestampToDateTime(t))

    def reads(json: JsValue): JsResult[Timestamp] = fromJson[DateTime](json).map(dateTimeToTimestamp)

  }

  implicit val formatter = Json.format[User]
}

case class Login(
                email: String,
                password: String,
                active: Boolean,
                id: Option[String] = None
                )

object Login {
  implicit val formatter = Json.format[Login]
}