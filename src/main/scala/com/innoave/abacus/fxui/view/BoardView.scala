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

import com.innoave.abacus.domain.model.AbacusSystem
import com.innoave.abacus.domain.model.Bead
import com.innoave.abacus.domain.model.Digit
import com.innoave.abacus.domain.model.DigitBead
import com.innoave.abacus.domain.model.NumeralSystem
import com.innoave.abacus.domain.model.Parameter
import com.innoave.abacus.domain.model.Position._
import com.innoave.abacus.domain.service.AbacusBuilder
import com.innoave.abacus.domain.service.AbacusService
import com.innoave.abacus.fxui.view.Orientation._
import scalafx.Includes._
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.VBox
import scalafx.scene.layout.HBox
import com.innoave.abacus.domain.model.BeadRod
import com.innoave.abacus.domain.model.DigitBeadRod

class BoardView[T <: Bead](
    val abacus: AbacusService[T],
    val numberOfRods: Option[Int] = None
    )(
    implicit val params: Parameter
    ) extends BorderPane {

  styleClass += "board-view"

  stylesheets += abacus.abacusSystem.stylesheet

  private val abacusBuilder = new AbacusBuilder(abacus.abacusSystem, abacus.numeralSystem)

  val heavenDeck: Option[DeckView[T]] = abacusBuilder.buildHeavenDeck(
      numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)
    ).map { rods: Seq[BeadRod[T]] =>
      abacus.abacusSystem.clearedAt match {
        case Top => new HDeckView(abacus, rods, TopToBottom)
        case Bottom => new HDeckView(abacus, rods, BottomToTop)
        case Left => new VDeckView(abacus, rods, LeftToRight)
        case Right => new VDeckView(abacus, rods, RightToLeft)
        case Outmost => new HDeckView(abacus, rods, TopToBottom)
        case Innermost => new HDeckView(abacus, rods, BottomToTop)
      }
    }

  val earthDeck: DeckView[T] = {
    val rods: Seq[BeadRod[T]] = abacusBuilder.buildEarthDeck(
        numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods))
    abacus.abacusSystem.clearedAt match {
      case Top => new HDeckView(abacus, rods, TopToBottom)
      case Bottom => new HDeckView(abacus, rods, BottomToTop)
      case Left => new VDeckView(abacus, rods, LeftToRight)
      case Right => new VDeckView(abacus, rods, RightToLeft)
      case Outmost => new HDeckView(abacus, rods, BottomToTop)
      case Innermost => new HDeckView(abacus, rods, TopToBottom)
    }
  }

  private def createPane = abacus.abacusSystem.clearedAt match {
      case Top => new VBox
      case Bottom => new VBox
      case Left => new HBox
      case Right => new HBox
      case Outmost => new VBox
      case Innermost => new VBox
    }

  val deckPane = createPane

  if (heavenDeck.isDefined) {
    deckPane.children += heavenDeck.get
  }
  deckPane.children += earthDeck

  center = deckPane

  abacus.abacusSystem.clearedAt match {
    case Top =>
      top = new HNumeralView(abacus, Seq.fill(numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)) {
          new Digit('0')
        })
    case Bottom =>
      top = new HNumeralView(abacus, Seq.fill(numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)) {
          new Digit('0')
        })
    case Left =>
      right = new VNumeralView(abacus, Seq.fill(numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)) {
          new Digit('0')
        })
    case Right =>
      left = new VNumeralView(abacus, Seq.fill(numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)) {
          new Digit('0')
        })
    case Outmost =>
      top = new HNumeralView(abacus, Seq.fill(numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)) {
          new Digit('0')
        })
    case Innermost =>
      top = new HNumeralView(abacus, Seq.fill(numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)) {
          new Digit('0')
        })
  }

}
