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
import com.innoave.abacus.domain.model.BeadRod
import com.innoave.abacus.domain.model.Digit
import com.innoave.abacus.domain.model.NumeralSystem
import com.innoave.abacus.domain.model.Parameter
import com.innoave.abacus.domain.model.Position._
import com.innoave.abacus.domain.service.AbacusBuilder
import com.innoave.abacus.domain.service.AbacusService
import com.innoave.abacus.domain.service.EventBus
import com.innoave.abacus.domain.service.event.BeadsMoved
import com.innoave.abacus.domain.service.event.DigitChanged
import com.innoave.abacus.fxui.view.Orientation._
import scalafx.Includes._
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.VBox
import scalafx.scene.layout.HBox

class BoardView[T <: Bead](
    val abacus: AbacusService[T],
    val numberOfRods: Option[Int] = None
    )(
    implicit val params: Parameter
    ) extends BorderPane {

  styleClass += "board-view"

  stylesheets += abacus.abacusSystem.stylesheet

  private val abacusBuilder = new AbacusBuilder(abacus.abacusSystem, abacus.numeralSystem)

  val heavenDeck: Option[DeckView[T]] =
    abacusBuilder.buildHeavenBeadRods(
        numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)).map { rods: Seq[BeadRod[T]] =>
            val orientation = abacus.abacusSystem.clearedAt match {
              case Top => TopToBottom
              case Bottom => BottomToTop
              case Left => LeftToRight
              case Right => RightToLeft
              case Outmost => TopToBottom
              case Innermost => BottomToTop
            }
            new DeckView(abacus, rods, orientation)
        }

  val earthDeck: DeckView[T] = {
    val rods: Seq[BeadRod[T]] = abacusBuilder.buildEarthBeadRods(
        numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods))
    val orientation = abacus.abacusSystem.clearedAt match {
      case Top => TopToBottom
      case Bottom => BottomToTop
      case Left => LeftToRight
      case Right => RightToLeft
      case Outmost => BottomToTop
      case Innermost => TopToBottom
    }
    new DeckView(abacus, rods, orientation)
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
  deckPane.styleClass += "deck-pane"

  if (heavenDeck.isDefined) {
    deckPane.children += heavenDeck.get
  }
  deckPane.children += earthDeck

  center = deckPane

  abacus.abacusSystem.clearedAt match {
    case Top =>
      top = buildHorizontalNumeralView
    case Bottom =>
      top = buildHorizontalNumeralView
    case Left =>
      right = buildVerticalNumeralView
    case Right =>
      left = buildVerticalNumeralView
    case Outmost =>
      top = buildHorizontalNumeralView
    case Innermost =>
      top = buildHorizontalNumeralView
  }

  private def buildHorizontalNumeralView = new HNumeralView(abacus,
      Seq.fill(numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)) {
          new Digit('0')
        }
    )

  private def buildVerticalNumeralView = new VNumeralView(abacus,
      Seq.fill(numberOfRods.getOrElse(abacus.abacusSystem.typicalNumberOfRods)) {
          new Digit('0')
        }
    )

  EventBus.of(abacus).register(classOf[BeadsMoved[T]], { ev: BeadsMoved[T] =>
      val position = ev.newBeadRod.position
      ev.newBeadRod.beadValue match {
        case 5 => //moved beads are on heaven deck
          earthDeck.beadRodFor(position, 1).foreach { beadRod =>
              val oldDigit = abacus.digitFor(beadRod, Some(ev.oldBeadRod))
              val newDigit = abacus.digitFor(beadRod, Some(ev.newBeadRod))
              EventBus.of(abacus).send(DigitChanged(position, oldDigit, newDigit))
            }
        case 1 => //moved beads are on earth deck
          val upperBeadRod = heavenDeck.flatMap { x => x.beadRodFor(position, 5) }
          val oldDigit = abacus.digitFor(ev.oldBeadRod, upperBeadRod)
          val newDigit = abacus.digitFor(ev.newBeadRod, upperBeadRod)
          EventBus.of(abacus).send(DigitChanged(position, oldDigit, newDigit))
      }
    })

}
