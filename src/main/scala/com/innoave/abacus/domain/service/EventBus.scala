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
package com.innoave.abacus.domain.service

import scala.collection.{mutable => mutable}
import com.innoave.abacus.domain.model.AbacusSystem
import com.innoave.abacus.domain.model.Bead
import com.innoave.abacus.domain.service.event.Event

trait EventBus {

  def register[T <: Event](eventType: Class[T], onEvent: T => Any): Unit
  def unregister[T <: Event](eventType: Class[T], onEvent: T => Any): Unit

  def send(event: Event): Unit

}

object EventBus {

  private val instances: mutable.Map[AbacusService[_], EventBus] = mutable.Map()

  def of(abacusService: AbacusService[_]): EventBus =
    instances.getOrElseUpdate(abacusService, { new EventBusImpl })

}

class EventBusImpl extends EventBus {

  private val eventHandler: mutable.Map[String, Set[Event => Any]] = mutable.Map()

  override def register[T <: Event](eventType: Class[T], onEvent: T => Any) {
    val handlerSet = eventHandler.getOrElse(eventType.getName, Set[Event => Any]())
    eventHandler.put(eventType.getName, handlerSet + onEvent.asInstanceOf[Event => Any])
  }

  override def unregister[T <: Event](eventType: Class[T], onEvent: T => Any) {
    val handlerSet = eventHandler.get(eventType.getName)
    if (handlerSet.isDefined) {
      eventHandler.put(eventType.getName, handlerSet.get - onEvent.asInstanceOf[Event => Any])
    }
  }

  override def send(event: Event) {
    eventHandler.get(event.getClass.getName).foreach { handlerSet =>
      handlerSet.foreach { handler =>
        handler(event)
      }
    }
  }

}
