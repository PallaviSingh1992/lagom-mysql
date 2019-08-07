package com.programmersnest.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.transport.Method.POST
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import com.programmersnest.api.models.User

trait UserService extends Service {

  def addNewUser: ServiceCall[User, Done]

  def findAllUsers(limit: Int): ServiceCall[NotUsed, List[User]]

  def findUserDetail(id: String): ServiceCall[NotUsed, User]

  override def descriptor: Descriptor = {
    import Service._

    named("user").withCalls(
      restCall(POST, "/api/user", addNewUser _),
      pathCall("/api/users?limit", findAllUsers _),
      pathCall("/api/user/:id", findUserDetail _)
    ).withAutoAcl(true)
  }
}
