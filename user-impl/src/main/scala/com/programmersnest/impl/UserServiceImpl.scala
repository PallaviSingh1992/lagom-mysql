package com.programmersnest.impl

import java.sql.{ResultSet, Timestamp}

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.NotFound
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import com.lightbend.lagom.scaladsl.server.ServerServiceCall
import com.programmersnest.api
import com.programmersnest.api.UserService
import com.programmersnest.api.models.User
import com.programmersnest.impl.repository.UserRepository
import com.programmersnest.utility.{Constant, Utility}
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl(registry: PersistentEntityRegistry,
                      userRepository: UserRepository)(implicit ec: ExecutionContext) extends UserService {

  private val log = LoggerFactory.getLogger(classOf[UserServiceImpl])

  private def entityRef(userId: String) = registry.refFor[UserEntity](userId)

  override def addNewUser: ServiceCall[User, Done] = ServerServiceCall { user =>
    val newUser = user.copy(id = Some(Utility.generateUUID),
      date = Some(new Timestamp(System.currentTimeMillis())), status = Some(Constant.INACTIVE))

    log.info(s"New user with ${newUser.id.get} id will added.")
    entityRef(newUser.id.get).ask(AddNewUser(newUser))
  }

  override def findAllUsers(limit: Int): ServiceCall[NotUsed, List[User]] = ServerServiceCall { _ =>
    userRepository.getUsers()
  }

  override def findUserDetail(id: String): ServiceCall[NotUsed, User] = ServerServiceCall { _ =>
    log.info(s"find $id user detail ... ")
    entityRef(id).ask(UserDetail(id)) map {
      case Some(user) => user
      case None => throw NotFound(s"User with ${id} id not found.")
    }
  }

  private def eventsResolver(userEvents: EventStreamElement[UserEvent]): api.UserEvent = {
    userEvents.event match {
      case UserAdded(userId, user) => api.UserAdded(userId, user)
    }
  }
}
