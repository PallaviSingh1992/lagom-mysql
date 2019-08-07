package com.programmersnest.impl

import akka.{Done, NotUsed}
import akka.persistence.query.Offset
import akka.stream.scaladsl.Flow
import com.lightbend.lagom.scaladsl.persistence.ReadSideProcessor.ReadSideHandler
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, EventStreamElement, ReadSideProcessor}
import com.programmersnest.impl.repository.UserRepository

import scala.concurrent.{ExecutionContext, Future}


class UserEventProcessor(userRepository: UserRepository)(implicit ec: ExecutionContext)
  extends ReadSideProcessor[UserEvent] {

  override def buildHandler(): ReadSideProcessor.ReadSideHandler[UserEvent] = {
    new ReadSideHandler[UserEvent] {
      override def handle(): Flow[EventStreamElement[UserEvent], Done, NotUsed] = {
        Flow[EventStreamElement[UserEvent]].mapAsync(4) { eventElement => {
          handleEvent(eventElement.event, eventElement.offset)
        }}
      }
    }
  }

  private def handleEvent(eventStreamElement: UserEvent, offset: Offset): Future[Done] = {
    eventStreamElement match {
      case newUser: UserAdded => userRepository.addNewUser(newUser.user)
    }
  }

  override def aggregateTags: Set[AggregateEventTag[UserEvent]] = Set(UserEvent.INSTANCE)
}
