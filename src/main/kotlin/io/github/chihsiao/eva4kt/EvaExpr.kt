package io.github.chihsiao.eva4kt

import io.github.chihsiao.eva4kt.EvaProgram.Builder
import io.github.chihsiao.eva4kt.enums.EvaOp

class EvaExpr internal constructor(
    val term: EvaTerm, val program: Builder
) {
    operator fun unaryMinus(): EvaExpr =
        EvaExpr(program.makeTerm(EvaOp.Negate, arrayOf(term)), program)

    private fun biCompute(op: EvaOp, anotherTerm: EvaTerm): EvaExpr {
        if (anotherTerm.program != this.program) {
            throw IllegalArgumentException("illegal ownership")
        }

        return EvaExpr(program.makeTerm(op, arrayOf(term, anotherTerm)), program)
    }

    operator fun plus(anotherTerm: EvaTerm) = biCompute(EvaOp.Add, anotherTerm)
    operator fun minus(anotherTerm: EvaTerm) = biCompute(EvaOp.Sub, anotherTerm)
    operator fun times(anotherTerm: EvaTerm) = biCompute(EvaOp.Mul, anotherTerm)

    infix fun pow(exponent: Int): EvaExpr {
        if (exponent < 1) {
            throw IllegalArgumentException("exponent must be greater than zero, got $exponent")
        }

        var result = term
        repeat(exponent) {
            result = program.makeTerm(EvaOp.Mul, arrayOf(result, term))
        }

        return EvaExpr(result, program)
    }

    infix fun shl(rotation: Int): EvaExpr =
        EvaExpr(program.makeLeftRotation(term, rotation), program)

    infix fun shr(rotation: Int): EvaExpr =
        EvaExpr(program.makeRightRotation(term, rotation), program)

    operator fun plus(expr: EvaExpr) = plus(expr.term)
    operator fun plus(vector: DoubleArray) = plus(program.makeDenseConstant(vector))
    operator fun plus(num: Double) = plus(program.makeUniformConstant(num))

    operator fun minus(expr: EvaExpr) = minus(expr.term)
    operator fun minus(vector: DoubleArray) = minus(program.makeDenseConstant(vector))
    operator fun minus(num: Double) = minus(program.makeUniformConstant(num))

    operator fun times(expr: EvaExpr) = times(expr.term)
    operator fun times(vector: DoubleArray) = times(program.makeDenseConstant(vector))
    operator fun times(num: Double) = times(program.makeUniformConstant(num))
}
