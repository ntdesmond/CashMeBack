package io.github.ntdesmond.cashmeback

import zio.{Task, ZIO, ZIOAppDefault}

object Main extends ZIOAppDefault:

  def run: Task[Unit] = {
    for
      config <- ZIO.service[AppConfig]
      _ <- zio.Console.printLine(config)
    yield ()
  }.provide(AppConfig.live)
