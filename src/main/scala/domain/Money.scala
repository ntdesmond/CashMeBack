package io.github.ntdesmond.cashmeback
package domain

import zio.prelude.*
import zio.prelude.Assertion.*

object Money extends Subtype[BigDecimal]:
  private val scale = 2

  override def wrap(value: BigDecimal): Money.Type = Money(
    value.setScale(scale),
  )

type Money = Money.Type
