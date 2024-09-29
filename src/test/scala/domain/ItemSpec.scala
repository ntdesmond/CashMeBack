package io.github.ntdesmond.cashmeback
package domain

import zio.test.*
import Generators.*
import zio.Scope

object ItemSpec extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = suite("ItemSpec")(
    suite("Distributable")(
      test("Buyers cannot take more items than available") {
        check(distributableItemGen, userGen, userGen) {
          (item, user, otherUser) =>
            for {
              error1 <- DistributableItem
                .addBuyer(user.id)
                .repeatN(item.amount + 1)
                .flip
                .toZIOWith(item)
              fullItem <- DistributableItem
                .addBuyer(user.id)
                .repeatN(item.amount - 1)
                .toZIOWithState(item)
                .map(_._1)
              error2 <- DistributableItem
                .addBuyer(otherUser.id)
                .flip
                .toZIOWith(fullItem)
            } yield assertTrue(
              item.freeAmount == item.amount,
              error1 == Error.NoBuySlots,
              fullItem.freeAmount == 0,
              fullItem.buyers.get(user.id).contains(fullItem.amount),
              error2 == Error.NoBuySlots,
            )
        }
      },
    ),
    suite("EquallySplit")(
      test("Users can only take item once") {
        check(equallySplitItemGen, userGen, userGen) {
          (item, user, otherUser) =>
            for {
              withFirst <- EquallySplitItem
                .addBuyer(user.id)
                .repeatN(10)
                .toZIOWithState(item)
                .map(_._1)
              withSecond <- EquallySplitItem
                .addBuyer(otherUser.id)
                .repeatN(10)
                .toZIOWithState(withFirst)
                .map(_._1)
            } yield assertTrue(
              item.amountPerBuyer.isEmpty,
              withFirst.buyers == Set(user.id),
              withFirst.amountPerBuyer.contains(item.price),
              withSecond.buyers == Set(user.id, otherUser.id),
              withSecond.amountPerBuyer.contains(Money.wrap(item.price / 2)),
            )
        }
      },
    ),
  )

}
