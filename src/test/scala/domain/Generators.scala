package io.github.ntdesmond.cashmeback
package domain

import zio.test.*
import zio.prelude.*
import zio.prelude.newtypes.*

object Generators {

  val moneyGen: Gen[Any, Money] = Gen.bigDecimal(0, 1000).map { value =>
    Money(value.setScale(2))
  }

  val userIdGen: Gen[Any, UserId.Type] = Gen.int.map(UserId.apply)
  val checkIdGen: Gen[Any, CheckId.Type] = Gen.uuid.map(CheckId.apply)
  val usernameGen: Gen[Any, Option[String]] =
    Gen.option(Gen.alphaNumericStringBounded(4, 32))
  val displayNameGen: Gen[Any, Option[String]] =
    Gen.option(Gen.string1(Gen.unicodeChar))

  val userGen: Gen[Any, User] = for {
    id <- userIdGen
    username <- usernameGen
    displayName <- displayNameGen
  } yield User(id, username, displayName)

  val checkGen: Gen[Any, Check] = for {
    id <- checkIdGen
    createdBy <- userIdGen
  } yield Check(id, createdBy)

  val itemIdGen: Gen[Any, ItemId.Type] = Gen.uuid.map(ItemId.apply)

  val itemNameGen: Gen[Any, String] = Gen.string1(Gen.unicodeChar)

  val distributableItemGen: Gen[Any, DistributableItem] = for {
    id <- itemIdGen
    checkId <- checkIdGen
    name <- itemNameGen
    price <- moneyGen
    amount <- Gen.int(1, 100)
  } yield DistributableItem(id, checkId, name, price, amount, Map.empty)

  val equallySplitItemGen: Gen[Any, EquallySplitItem] = for {
    id <- itemIdGen
    checkId <- checkIdGen
    name <- itemNameGen
    price <- moneyGen
    amount <- Gen.int(1, 100)
  } yield EquallySplitItem(id, checkId, name, price, Set.empty)

  val itemGen: Gen[Any, Item] =
    Gen.oneOf(distributableItemGen, equallySplitItemGen)
}
