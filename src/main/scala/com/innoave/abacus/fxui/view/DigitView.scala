/*
 * Copyright (c) 2016, Innoave.com
 * All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL INNOAVE.COM OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.innoave.abacus.fxui.view

import scala.collection.JavaConversions._
import scala.language.postfixOps
import com.innoave.abacus.domain.model.Digit
import eu.hansolo.enzo.splitflap.SplitFlap
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.animation.AnimationTimer
import scalafx.util.Duration

class DigitView(
    val initialDigit: Digit
    ) extends SplitFlap {

  getStyleClass += "digit-view"

  flipTime = Duration(50)
  setSelection(Array(" ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"))

  var animationTime: Duration = Duration(2E9)

  def flipTime = getFlipTime
  def flipTime_=(duration: Duration) {
    setFlipTime(duration.toMillis)
  }

  val digit = ObjectProperty(initialDigit)

  digit onChange { (_, oldValue, newValue) =>
    animateDisplayChange(newValue, animationTime)
  }

  def resetDisplay(duration: Duration = animationTime) {
    animateDisplayChange(initialDigit, duration)
  }

  private def animateDisplayChange(newValue: Digit, duration: Duration) {
    val nanoDuration = duration.toMillis * 1E6
    val start = System.nanoTime
    AnimationTimer { now =>
      if (now > start + nanoDuration) {
        setText(newValue.toChar)
      }
    } start
  }

}
