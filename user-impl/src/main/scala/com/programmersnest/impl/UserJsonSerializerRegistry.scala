package com.programmersnest.impl

import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}

import scala.collection.immutable


object UserJsonSerializerRegistry extends JsonSerializerRegistry {

  override def serializers: immutable.Seq[JsonSerializer[_]] = Vector(

    //Commands
    JsonSerializer[AddNewUser],

    //Events
    JsonSerializer[UserAdded],

    //State
    JsonSerializer[UserEntityState]

  )
}
