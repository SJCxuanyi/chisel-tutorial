// See LICENSE.txt for license details.
package problems

import chisel3._
import chisel3.util.{Valid, DeqIO}

// Problem:
// Implement a GCD circuit (the greatest common divisor of two numbers).
// Input numbers are bundled as 'RealGCDInput' and communicated using the Decoupled handshake protocol
//
class RealGCDInput extends Bundle {
  val a = UInt(16.W)
  val b = UInt(16.W)
}

class RealGCD extends Module {
  val io  = IO(new Bundle {
    val in  = DeqIO(new RealGCDInput())
    val out = Output(Valid(UInt(16.W)))
  })

  // Implement below ----------
  val x = Reg(UInt(16.W))
  val y = Reg(UInt(16.W))
  val ready = RegInit(true.B)
  io.in.ready := ready
  when(io.in.valid && io.in.ready){
    x := io.in.bits.a
    y := io.in.bits.b
    ready := false.B
  }
  when(!ready){
    when(x > y) {
      x := x - y
    }.otherwise {
      y := y - x
    }
  }

  io.out.valid := (y === 0.U && !ready)
  io.out.bits := x
  when(io.out.valid){
    ready := true.B
  }
  // Implement above ----------

}
