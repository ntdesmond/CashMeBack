package io.github.ntdesmond.cashmeback
package domain

import io.github.ntdesmond.cashmeback
import zio.UIO
import zio.prelude.Subtype
import zio.prelude.fx.ZPure

import java.util.UUID

sealed trait Item:
  val id: ItemId.Type
  val checkId: CheckId.Type
  val name: String
  val price: Money

object ItemId extends Subtype[UUID]

case class DistributableItem(
  id: ItemId.Type,
  checkId: CheckId.Type,
  name: String,
  price: Money,
  amount: Int,
  buyers: Map[UserId.Type, Int],
) extends Item:
  val freeAmount: Int = amount - buyers.values.sum

object DistributableItem:
  def create(
    checkId: CheckId.Type,
    name: String,
    price: Money,
    amount: Int,
  ): UIO[DistributableItem] =
    zio
      .Random
      .nextUUID
      .map(ItemId.apply)
      .map { id =>
        DistributableItem(id, checkId, name, price, amount, Map.empty)
      }

  def addBuyer(
    buyer: UserId.Type,
  ): ZPure[Nothing, DistributableItem, DistributableItem, Any, Error, Unit] =
    ZPure
      .get[DistributableItem]
      .filterOrFail(_.freeAmount > 0)(Error.NoBuySlots)
      .zipRight(ZPure.update { item =>
        item.copy(buyers =
          item.buyers.updated(buyer, item.buyers.getOrElse(buyer, 0) + 1),
        )
      })

case class EquallySplitItem(
  id: ItemId.Type,
  checkId: CheckId.Type,
  name: String,
  price: Money,
  buyers: Set[UserId.Type],
) extends Item:
  val amountPerBuyer: Option[Money] =
    Option.when(buyers.nonEmpty)(Money.wrap(price / buyers.size))

object EquallySplitItem:
  def create(
    checkId: CheckId.Type,
    name: String,
    price: Money,
    buyers: Set[UserId.Type],
  ): UIO[EquallySplitItem] =
    zio
      .Random
      .nextUUID
      .map(ItemId.apply)
      .map { id =>
        EquallySplitItem(id, checkId, name, price, buyers)
      }

  def addBuyer(
    buyer: UserId.Type,
  ): ZPure[Nothing, EquallySplitItem, EquallySplitItem, Any, Error, Unit] =
    ZPure
      .get[EquallySplitItem]
      .zipRight(ZPure.update { item =>
        item.copy(buyers = item.buyers + buyer)
      })
