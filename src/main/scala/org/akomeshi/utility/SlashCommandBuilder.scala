package org.akomeshi
package utility

/**
 * Created by KaNguy - 08/09/2021
 * File org.akomeshi.utility/SlashCommandBuilder.scala
 */

import java.util

object SlashCommandBuilder {
  /**
   * Application command types.
   *
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-types]]
   */
  type CommandType = Int
  val CHAT_INPUT: CommandType = 1
  val USER: CommandType = 2
  val MESSAGE: CommandType = 3

  /**
   * Creates a slash command.
   *
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object]]
   * @param name        Name of the slash command.
   * @param commandType Command type.
   * @return
   */
  def createSlashCommand(name: String, commandType: Int): Commands = new Commands()

  /**
   * @see [[https://discord.com/developers/docs/interactions/application-commands#application-command-object]]
   */
  class Commands(private val command: util.HashMap[String, Any] = new util.HashMap()) {

    def label(name: String, description: String, commandType: CommandType): Commands = {
      command.put("name", name)
      command.put("type", commandType)
      command.put("description", description)
      Commands.this
    }

    def addOption(name: String, description: String, commandType: CommandType, required: Boolean, choices: Array[util.HashMap[String, String]] = Array.empty[util.HashMap[String, String]]): Commands = {
      val option: util.HashMap[String, Any] = new util.HashMap[String, Any]()
      option.put("name", name)
      option.put("description", description)
      option.put("type", commandType)
      option.put("required", required.toString)
      if (choices.nonEmpty) option.put("choices", choices)
      Commands.this
    }

    def choiceBuilder(name: String, value: String): util.HashMap[String, String] = {
      util.Map.of("name", name, "value", value).asInstanceOf[util.HashMap[String, String]]
    }

    def getCommand: util.HashMap[String, Any] = command
  }
}