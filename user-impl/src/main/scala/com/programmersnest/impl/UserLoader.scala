package com.programmersnest.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.jdbc.JdbcPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.programmersnest.api.UserService
import com.programmersnest.impl.repository.UserRepository
import com.softwaremill.macwire.wire
import play.api.db.HikariCPComponents
import play.api.libs.ws.ahc.AhcWSComponents


abstract class UserComponents(context: LagomApplicationContext) extends LagomApplication(context)
  with JdbcPersistenceComponents with HikariCPComponents with AhcWSComponents {

  override lazy val jsonSerializerRegistry = UserJsonSerializerRegistry

  override def lagomServer: LagomServer = serverFor[UserService](wire[UserServiceImpl])

  persistentEntityRegistry.register(wire[UserEntity])
  lazy val userRepository = wire[UserRepository]
  readSide.register(wire[UserEventProcessor])
}

abstract class UserApplication (context: LagomApplicationContext) extends UserComponents(context) with LagomKafkaComponents

class UserLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new UserApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new UserApplication(context) with LagomDevModeComponents
}