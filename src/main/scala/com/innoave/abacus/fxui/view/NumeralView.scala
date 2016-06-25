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
import com.innoave.abacus.domain.model.Number
import com.innoave.abacus.domain.model.Parameter
import com.innoave.abacus.domain.service.AbacusService
import com.innoave.abacus.domain.service.EventBus
import com.innoave.abacus.domain.service.event.DigitChanged
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.scene.layout.HBox
import scalafx.scene.layout.Pane
import scalafx.scene.layout.VBox
import scalafx.scene.layout.GridPane

trait NumeralView extends GridPane {

  styleClass += "numeral-view"

  def abacusService: AbacusService[_]

  def digitViews: Seq[DigitView]

  def digitViewFor(position: Int): Option[DigitView] =
    if (position < digitViews.size)
      Some(digitViews(digitViews.size - 1 - position))
    else
      None

  def setDigit(position: Int, value: Digit) {
    digitViewFor(position).foreach { digitView =>
        digitView.digit() = value
      }
  }

  protected def buildDigitView(digit: Digit, h: Int): DigitView =
    new DigitView(digit, abacusService.numeralSystem) {
      scaleSplitFlapTo(h)
    }

  EventBus.of(abacusService).register(classOf[DigitChanged], { ev: DigitChanged =>
      setDigit(ev.position, ev.newDigit)
    })

}

class HNumeralView(
    override val abacusService: AbacusService[_],
    val initialDigits: Seq[Digit]
    )(
    implicit params: Parameter
    ) extends NumeralView {

  override val digitViews: Seq[DigitView] =
    for {
      digit <- initialDigits
    } yield {
      buildDigitView(digit, params.beadWidth)
    }

  addRow(0, (digitViews.map { v => Pane.sfxPane2jfx(v) }): _*)

}

class VNumeralView(
    override val abacusService: AbacusService[_],
    val initialDigits: Seq[Digit]
    )(
    implicit params: Parameter
    ) extends NumeralView {

  override val digitViews: Seq[DigitView] =
    for {
      digit <- initialDigits
    } yield {
      buildDigitView(digit, params.beadWidth)
    }

  addColumn(0, (digitViews.map { v => Pane.sfxPane2jfx(v) }): _*)

}
