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
package com.innoave.abacus.app

import com.innoave.abacus.model.AbacusSystem
import com.innoave.abacus.model.Bead
import com.innoave.abacus.model.BeadRod
import com.innoave.abacus.model.Decimal
import com.innoave.abacus.model.DefaultParameter
import com.innoave.abacus.model.NumeralSystem
import com.innoave.abacus.model.Position._
import com.innoave.abacus.model.Soroban
import com.innoave.abacus.service.AbacusBuilder
import com.innoave.abacus.view.BoardView
import com.innoave.abacus.view.Orientation._
import com.innoave.abacus.view.RodView
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.shape.Circle
import scalafx.scene.layout.VBox

object FxUiApp extends JFXApp {

  implicit val params = DefaultParameter

  private val abacusBuilder = new AbacusBuilder(Soroban, Decimal)

  stage = new PrimaryStage {
    title = "Abacus SFX"
    width = 1200
    height = 800

    val heavenDeck = abacusBuilder.buildHeavenDeck().map { rods => new BoardView(rods, TopToBottom) }
    val earthDeck = new BoardView(abacusBuilder.buildEarthDeck(), BottomToTop)

    scene = new Scene {
      stylesheets += "/styles/soroban.css"
      content = new VBox {
        if (heavenDeck.isDefined) {
          children += heavenDeck.get
        }
        children += earthDeck
      }
    }
  }

}
