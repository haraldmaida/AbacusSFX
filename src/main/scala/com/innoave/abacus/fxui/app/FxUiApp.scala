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

import com.innoave.abacus.domain.model.Decimal;
import com.innoave.abacus.domain.model.DefaultParameter;
import com.innoave.abacus.domain.model.Schoty;
import com.innoave.abacus.domain.model.Soroban;
import com.innoave.abacus.domain.model.Suanpan;
import com.innoave.abacus.domain.service.AbacusBuilder
import com.innoave.abacus.fxui.view.BoardView
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.BorderPane
import com.innoave.abacus.domain.service.AbacusService

object FxUiApp extends JFXApp {

  implicit val params = DefaultParameter

  val sorobanView = new BoardView(AbacusService.of(Soroban, Decimal))
  val suanbanView = new BoardView(AbacusService.of(Suanpan, Decimal))
  val schotyView = new BoardView(AbacusService.of(Schoty, Decimal))

  val scenePanel = new BorderPane {
    styleClass += "abacus"
    center = new StackPane {
//      children += schotyView
      children += sorobanView
//      children += suanbanView
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
