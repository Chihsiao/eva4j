package io.github.chihsiao.eva4kt

import io.github.chihsiao.eva4kt.enums.EvaOp

class EvaExpr internal constructor(
        val builder: EvaProgramBuilder,
        val term: EvaTerm
) {
    operator fun unaryMinus(): EvaExpr =
            EvaExpr(builder, builder.makeTerm(EvaOp.Negate, arrayOf(term)))

    private fun biCompute(op: EvaOp, anotherTerm: EvaTerm): EvaExpr {
        if (anotherTerm.builder != this.builder) throw IllegalArgumentException("illegal ownership")
        return EvaExpr(builder, builder.makeTerm(op, arrayOf(term, anotherTerm)))
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
            result = builder.makeTerm(EvaOp.Mul, arrayOf(result, term))
        }

        return EvaExpr(builder, result)
    }

    infix fun shl(rotation: Int) = EvaExpr(builder, builder.makeLeftRotation(term, rotation))
    infix fun shr(rotation: Int) = EvaExpr(builder, builder.makeRightRotation(term, rotation))

    operator fun plus(expr: EvaExpr) = plus(expr.term)
    operator fun plus(vector: DoubleArray) = plus(builder.makeDenseConstant(vector))
    operator fun plus(num: Double) = plus(builder.makeUniformConstant(num))

    operator fun minus(expr: EvaExpr) = minus(expr.term)
    operator fun minus(vector: DoubleArray) = minus(builder.makeDenseConstant(vector))
    operator fun minus(num: Double) = minus(builder.makeUniformConstant(num))

    operator fun times(expr: EvaExpr) = times(expr.term)
    operator fun times(vector: DoubleArray) = times(builder.makeDenseConstant(vector))
    operator fun times(num: Double) = times(builder.makeUniformConstant(num))
}
