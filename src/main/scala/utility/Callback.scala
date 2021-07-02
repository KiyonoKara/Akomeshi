package org.akomeshi
package utility

/**
 * Created by KaNguy - 6/29/2021
 * File utility/Callback.scala
 */

trait Callback[T] {
  def call(channel: String, target: T)
}