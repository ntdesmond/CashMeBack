package io.github.ntdesmond.cashmeback
package domain

import zio.prelude.Subtype

import java.util.UUID

case class Check(id: CheckId.Type, createdBy: UserId.Type)

object CheckId extends Subtype[UUID]
