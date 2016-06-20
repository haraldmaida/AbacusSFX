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
  final def digits: Seq[Digit] = Seq.range(0, radix.value).map { n => digitFor(n) }
  def digitFor(value: Int): Digit
}

sealed trait Binary extends NumeralSystem {
  override final val radix = Radix(2)
  override final def digitFor(value: Int) = Digit((0x30 + value).toChar)
}

object Binary extends Binary

sealed trait Octal extends NumeralSystem {
  override final val radix = Radix(8)
  override final def digitFor(value: Int) = Digit((0x30 + value).toChar)
}

object Octal extends Octal

sealed trait Decimal extends NumeralSystem {
  override final val radix = Radix(10)
  override final def digitFor(value: Int) = Digit((0x30 + value).toChar)
}

object Decimal extends Decimal

sealed trait Hexadecimal extends NumeralSystem {
  override final val radix = Radix(16)
  override final def digitFor(value: Int) =
    if (0 <= value && value < 10)
      Digit((0x30 + value).toChar)
    else
      Digit((0x31 + value).toChar)
}

object Hexadecimal extends Hexadecimal
