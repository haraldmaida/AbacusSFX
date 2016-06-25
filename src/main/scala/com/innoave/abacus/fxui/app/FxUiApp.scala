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
package com.innoave.abacus.fxui.app

import com.innoave.abacus.domain.model.Bead
import com.innoave.abacus.domain.model.Decimal;
import com.innoave.abacus.domain.model.DefaultParameter;
import com.innoave.abacus.domain.model.Schoty;
import com.innoave.abacus.domain.model.Soroban;
import com.innoave.abacus.domain.model.Suanpan;
import com.innoave.abacus.domain.service.AbacusBuilder
import com.innoave.abacus.domain.service.AbacusService
import com.innoave.abacus.fxui.view.BoardView
import scalafx.Includes._
import scalafx.animation.TranslateTransition
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.Pane
import scalafx.scene.layout.StackPane
import scalafx.scene.control.Button
import scalafx.util.Duration

object FxUiApp extends JFXApp {

  implicit val params = DefaultParameter

  val abacusViews: Seq[BoardView[_ <: Bead]] = Seq(
      new BoardView(AbacusService.of(Soroban, Decimal)),
      new BoardView(AbacusService.of(Suanpan, Decimal)),
      new BoardView(AbacusService.of(Schoty, Decimal))
      )

  val currentView: ObjectProperty[Option[BoardView[_]]] = ObjectProperty(None)
  currentView onChange { (_, currView, nextView) =>
      currView.foreach { view0 =>
        new TranslateTransition(Duration(1E3), view0) {
            toX = view0.width()
            onFinished = { ev: ActionEvent =>
              view0.translateX = 2E4
              view0.visible = false
            }
          }.play
        }
      nextView.foreach { view1 =>
          view1.visible = true
          view1.translateX = -view1.width()
          new TranslateTransition(Duration(1E3), view1) {
            toX = 0
          }.play
        }
    }

  val scenePanel = new BorderPane {
    styleClass += "abacus"
    center = new StackPane {
      children ++= abacusViews.map { v => Node.sfxNode2jfx(v) }
    }
    top = new GridPane {
      styleClass += "board-header"
      val abacusButtons: Seq[Pane] = abacusViews.map { abacusView =>
          new Pane {
            children = new Button {
              text = abacusView.abacus.abacusSystem.name
              onAction = { ev: ActionEvent =>
                  currentView() = Some(abacusView)
                }
            }
          }
        }
      addRow(0, abacusButtons.map { v => Pane.sfxPane2jfx(v) }: _*)
    }
  }

  stage = new PrimaryStage {
    title = "Abacus SFX"
    scene = new Scene {
      stylesheets += "/styles/abacus.css"
      content = scenePanel
    }

  }

}
