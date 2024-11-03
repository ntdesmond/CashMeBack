package io.github.ntdesmond.cashmeback

import AppConfig.JdbcConfig

import com.typesafe.config.ConfigFactory
import zio.{Config, TaskLayer, ZLayer}
import zio.config.typesafe.TypesafeConfigProvider
import zio.config.magnolia.deriveConfig

case class AppConfig(
  jdbc: JdbcConfig,
)

object AppConfig:
  case class JdbcConfig(
    driver: String = "org.postgresql.Driver",
    url: String,
    user: String,
    password: String,
  )

  given Config[AppConfig] = deriveConfig[AppConfig]

  val live: TaskLayer[AppConfig] = ZLayer.fromZIO(
    TypesafeConfigProvider
      .fromTypesafeConfig(ConfigFactory.load())
      .load[AppConfig],
  )
