package io.github.ntdesmond.cashmeback
package domain

import scala.util.control.NoStackTrace

trait Error extends NoStackTrace

object Error:
  case object NoBuySlots extends Error
