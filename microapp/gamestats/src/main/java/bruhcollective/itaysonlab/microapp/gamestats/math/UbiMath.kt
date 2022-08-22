package bruhcollective.itaysonlab.microapp.gamestats.math

import com.github.keelar.exprk.Expressions

object UbiMath {
    private val regexVars = Regex("(\\w+)")
    private val regexPrecision = Regex("precision: (\\d+)")

    fun format(str: String, variableFunc: (String) -> Double): String {
        // that was probably for JS and evaled (maybe?)
        val evalFunc = str.removePrefix("format(").removeSuffix(")").split(", {")

        val formula = evalFunc[0]
        val parameters = evalFunc
            .getOrNull(1)
            ?.let { regexPrecision.find(it)?.groupValues?.getOrNull(1) }
            ?.let { FormatParams(notation = "", precision = it.toInt()) }

        return format(formula, variableFunc, parameters)
    }

    // the func itself
    // TODO: figure out what is notation
    fun format(formula: String, variableFunc: (String) -> Double, params: FormatParams?): String {
        val variables = regexVars.findAll(formula).map { it.groupValues[1] }
        return Expressions()
            .also { expr ->
                variables.forEach { variable ->
                    expr.define(variable, variableFunc(variable))
                }
            }
            .also { expr ->
                params?.let {
                    expr.setPrecision(params.precision)
                }
            }
            .evalToString(formula)
    }

    class FormatParams(
        val notation: String,
        val precision: Int
    )
}