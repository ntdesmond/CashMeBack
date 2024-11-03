package io.github.ntdesmond.cashmeback
package domain

import zio.UIO
import zio.prelude.Subtype

import java.util.UUID

case class Check(id: CheckId.Type, createdBy: UserId.Type)

object CheckId extends Subtype[UUID]

object Check:
  def create(
    by: UserId.Type,
  ): UIO[Check] =
    zio
      .Random
      .nextUUID
      .map(CheckId.apply)
      .map { id =>
        Check(id, by)
      }
