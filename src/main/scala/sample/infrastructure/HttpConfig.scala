package sample.infrastructure

import com.typesafe.config.Config

case class HttpConfig(host: String, port: Int)

object HttpConfig {
  def apply(config: Config): HttpConfig = HttpConfig(config.getString("http.host"), config.getInt("http.port"))
}
