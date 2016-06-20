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
package com.innoave.abacus.model

trait BeadRod[T <: Bead] {

  val position: Int

  val clearedBeads: Seq[T]
  val countedBeads: Seq[T]

  def moveBeads(touchedBead: Bead): BeadRod[T] =
    clearedBeads.indexOf(touchedBead) match {
      case -1 =>
        countedBeads.indexOf(touchedBead) match {
          case -1 =>
            this
          case i =>
            copy(
                clearedBeads ++ countedBeads.take(i + 1),
                countedBeads.takeRight(countedBeads.size - (i + 1))
                )
        }
      case i =>
        copy(
            clearedBeads.take(i),
            clearedBeads.takeRight(clearedBeads.size - i) ++ countedBeads
            )
    }

  def copy(clearedBeads: Seq[T], countedBeads: Seq[T]): BeadRod[T]

}
