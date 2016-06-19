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

trait NumeralSystem {
  def radix: Radix
  def digits: Seq[Digit]
}

trait Binary extends NumeralSystem {
  override final val radix = Radix(2)
  override final val digits = Seq.range(0, radix.value).map { n => Digit((0x30 + n).toChar) }
}

object Binary extends Binary

trait Octal extends NumeralSystem {
  override final val radix = Radix(8)
  override final val digits = Seq.range(0, radix.value).map { n => Digit((0x30 + n).toChar) }
}

object Octal extends Octal

trait Decimal extends NumeralSystem {
  override final val radix = Radix(10)
  override final val digits = Seq.range(0, radix.value).map { n => Digit((0x30 + n).toChar) }
}

object Decimal extends Decimal

trait Hexadecimal extends NumeralSystem {
  override final val radix = Radix(16)
  override final val digits = Seq.range(0, 10).map { n => Digit((0x30 + n).toChar) } ++
      Seq.range(0, 6).map { n => Digit((0x41 + n).toChar) }

}

object Hexadecimal extends Hexadecimal
