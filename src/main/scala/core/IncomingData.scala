package org.akomeshi
package core

import org.akomeshi.websocket.{AkoWebSocket, WebSocketListener, WebSocketEvents}

import java.net.http.WebSocket
import java.util.concurrent.CompletionStage

class IncomingData {

}

object IncomingData extends App {
  val wsListener = new WebSocketListener {
    override def onOpen(webSocket: WebSocket): Unit = {
      println("WebSocket connection opened")
      super.onOpen(webSocket)
    }

    override def onText(webSocket: WebSocket, data: CharSequence, last: Boolean): CompletionStage[_] = {
      println("Data: " + data)
      super.onText(webSocket, data, last)
    }

    override def onClose(webSocket: WebSocket, statusCode: Int, reason: String): CompletionStage[_] = {
      println("Connection closed: " + statusCode + ", " + reason)
      super.onClose(webSocket, statusCode, reason)
    }

    override def onError(webSocket: WebSocket, error: Throwable): Unit = {
      println("Error: " + error)
      super.onError(webSocket, error)
    }
  }

  val ws = new AkoWebSocket("wss://echo.websocket.org", wsListener)
  ws.close(1000, "Normal closure")
}
