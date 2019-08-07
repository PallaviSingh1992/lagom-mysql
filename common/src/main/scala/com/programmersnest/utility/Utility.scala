package com.programmersnest.utility

import java.util.UUID


object Utility {

  def generateUUID = UUID.randomUUID().toString.replaceAll("-", "")
}
