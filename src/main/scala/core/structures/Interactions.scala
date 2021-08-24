package org.akomeshi
package core.structures

/**
 * Created by KaNguy - 08/09/2021
 * File core/structures/Interactions.scala
 */

// Akomeshi
import core.api.request.RequestFrame
import utility.Constants
import utility.Utilities.toHashMap
import json.JSON
import core.managers.Cache

// Utilities
import java.util

case class Interactions() {
  private val readyCacheContents = Cache.readyCache.get(Constants.WebSocketEvents.map(_ => "READY").head)
  val applicationID: String = if (readyCacheContents != null) {
    toHashMap(toHashMap(readyCacheContents).get("application")).get("id").toString
  } else JSON.parseAsHashMap(RequestFrame.get(s"${Constants.apiURL}/v${Constants.APIVersion}/users/@me")).get("id").toString

  // TODO: This currently gets the application's commands and is raw but should do something different
  // TODO: Implement interactions and slash commands since message content will be a privileged intent
  def getCommands: util.HashMap[Any, Any] = {
    val request = RequestFrame.get(
      url = Constants.formatAPIURL + s"/applications/$applicationID/commands"
    )
    JSON.parseAsHashMap(s"{\"commands\":$request}")
  }

  /**
   * Deletes a global slash command
   * @see [[https://discord.com/developers/docs/interactions/application-commands#delete-global-application-command]]
   * @param commandID Provide the command ID
   * @return Returns 204, no content
   */
  def deleteGlobalCommand(commandID: String): String = {
    RequestFrame.delete(s"${Constants.formatAPIURL}/applications/$applicationID/commands/$commandID")
  }

  /**
   * Deletes a guild slash command
   * @see [[https://discord.com/developers/docs/interactions/application-commands#delete-guild-application-command]]
   * @param guildID Provide the guild ID
   * @param commandID Provide the command ID
   * @return Returns 204, no content
   */
  def deleteGuildCommand(guildID: String, commandID: String): String = {
    RequestFrame.delete(s"${Constants.formatAPIURL}/applications/$applicationID/guilds/$guildID/commands/$commandID")
  }
}
