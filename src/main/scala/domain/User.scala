package io.github.ntdesmond.cashmeback
package domain

import zio.prelude.Subtype

import java.util.UUID

case class User(
  id: UserId.Type,
  username: Option[String],
  displayName: Option[String]
)

object UserId extends Subtype[Int]
