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

import com.innoave.abacus.domain.model.Digit
import com.innoave.abacus.domain.model.NumeralSystem
import eu.hansolo.enzo.splitflap.SplitFlap
import eu.hansolo.enzo.splitflap.SplitFlapBuilder
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.scene.layout.Pane
import scalafx.util.Duration

class DigitView(
    val initialDigit: Digit,
    val numeralSystem: NumeralSystem
    ) extends Pane {

  styleClass += "digit-view"

  val splitFlap: SplitFlap = SplitFlapBuilder.create.
      selection(numeralSystem.digits.map { x => x.toChar.toString }.toArray).
      build

  def scaleSplitFlapTo(newHeight: Int) = {
    splitFlap.setMaxWidth(newHeight * splitFlap.getPrefWidth / splitFlap.getPrefHeight)
    splitFlap.setMaxHeight(newHeight)
  }

  flipTime = Duration(50)

  def flipTime = splitFlap.getFlipTime
  def flipTime_=(duration: Duration) {
    splitFlap.setFlipTime(duration.toMillis)
  }

  val digit = ObjectProperty(initialDigit)

  digit onChange { (_, oldValue, newValue) =>
    splitFlap.setText(newValue.toChar)
  }

  children += splitFlap

}
