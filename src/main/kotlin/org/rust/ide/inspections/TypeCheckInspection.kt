package org.rust.ide.inspections

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.rust.lang.core.psi.*
import org.rust.lang.core.psi.util.parentOfType
import org.rust.lang.core.type.RustUnitResolvedType
import org.rust.lang.core.type.RustUnknownType
import org.rust.lang.core.type.inferredType
import org.rust.lang.core.type.resolvedType

class TypeCheckInspection : RustLocalInspectionTool() {
    override fun getDisplayName(): String = "Type Check"

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : RustVisitor() {
            override fun visitFnItem(o: RustFnItem) {
                val blockType = o.block?.expr?.inferredType ?: RustUnknownType
                val retType = o.retType?.type?.resolvedType ?: RustUnitResolvedType
                when {
                    o.block == null -> {}
                    retType is RustUnitResolvedType -> {} // do nothing, the expression type doesn't actually matter
                    retType is RustUnknownType -> {} // do nothing, we can't check it
                    o.block?.expr == null -> holder.registerProblem(o.block!!, "Expected type ${retType.name}, got ${blockType.name}", ProblemHighlightType.GENERIC_ERROR)
                    blockType != retType -> holder.registerProblem(o.block?.expr!!, "Expected type ${retType.name}, got ${blockType.name}", ProblemHighlightType.GENERIC_ERROR)
                }
            }

            override fun visitRetExpr(o: RustRetExpr) {
                val fnParent = o.parentOfType<RustFnItem>();
                val implParent = o.parentOfType<RustImplMethodMember>();
                val traitParent = o.parentOfType<RustTraitMethodMember>();
                when {
                    fnParent != null -> {
                        val retType = fnParent.retType?.type?.resolvedType ?: RustUnitResolvedType
                        val exprType = o.expr?.inferredType ?: RustUnitResolvedType
                        if (retType !is RustUnknownType && exprType !is RustUnknownType && retType != exprType)
                            holder.registerProblem(o, "Expected type ${retType.name}, got ${exprType.name}", ProblemHighlightType.GENERIC_ERROR)
                    }
                    implParent != null -> {
                        val retType = implParent.retType?.type?.resolvedType ?: RustUnitResolvedType
                        val exprType = o.expr?.inferredType ?: RustUnitResolvedType
                        if (retType !is RustUnknownType && exprType !is RustUnknownType && retType != exprType)
                            holder.registerProblem(o, "Expected type ${retType.name}, got ${exprType.name}", ProblemHighlightType.GENERIC_ERROR)
                    }
                    traitParent != null -> {
                        val retType = traitParent.retType?.type?.resolvedType ?: RustUnitResolvedType
                        val exprType = o.expr?.inferredType ?: RustUnitResolvedType
                        if (retType !is RustUnknownType && exprType !is RustUnknownType && retType != exprType)
                            holder.registerProblem(o, "Expected type ${retType.name}, got ${exprType.name}", ProblemHighlightType.GENERIC_ERROR)
                    }
                }
            }

            override fun visitLetDecl(o: RustLetDecl) {
                val exprType = o.expr?.inferredType ?: RustUnknownType
                val ascriptedType = o.type?.resolvedType
                if (ascriptedType != null && ascriptedType !is RustUnknownType && exprType !is RustUnknownType && ascriptedType != exprType) {
                    holder.registerProblem(o.expr ?: o, "Expected type ${ascriptedType.name}, got ${exprType.name}", ProblemHighlightType.GENERIC_ERROR)
                }
            }

            override fun visitConstItem(o: RustConstItem) {
                val constType = o.type.resolvedType
                val exprType = o.expr.inferredType
                if (constType !is RustUnknownType && exprType !is RustUnknownType && constType != exprType)
                    holder.registerProblem(o.expr, "Expected type ${constType.name}, got ${exprType.name}", ProblemHighlightType.GENERIC_ERROR)
            }

            override fun visitStaticItem(o: RustStaticItem) {
                val staticType = o.type.resolvedType
                val exprType = o.expr.inferredType
                if (staticType !is RustUnknownType && exprType !is RustUnknownType && staticType != exprType)
                    holder.registerProblem(o.expr, "Expected type ${staticType.name}, got ${exprType.name}", ProblemHighlightType.GENERIC_ERROR)
            }

            override fun visitCallExpr(o: RustCallExpr) {
                val refFn = o.reference?.resolve()
                if (refFn != null && refFn is RustFnItem) {
                    if (refFn.parameters?.parameterList?.size != o.argList.exprList.size) {
                        val expParams = refFn.parameters?.parameterList?.size ?: 0
                        val gotParams = o.argList.exprList.size
                        holder.registerProblem(o, "Expected $expParams arguments, got $gotParams", ProblemHighlightType.GENERIC_ERROR)
                    } else {
                        val argTypes = o.argList.exprList.map { it.inferredType }
                        val paramTypes = refFn.parameters?.parameterList?.map { it.type?.resolvedType }.orEmpty()
                        for ((a, p) in argTypes.zip(paramTypes)) {
                            if (a !is RustUnknownType && p !is RustUnknownType && a != p) {
                                holder.registerProblem(o, "Expected type ${p?.name} argument, got ${a.name}", ProblemHighlightType.GENERIC_ERROR)
                            }
                        }
                    }
                }
            }

            override fun visitMatchExpr(o: RustMatchExpr) {
                // Check that all the arms' types match the first one
                val firstArm = o.matchBody.matchArmList.firstOrNull()
                if (firstArm != null) {
                    val firstExprType = firstArm.expr.inferredType
                    if (firstExprType is RustUnknownType) return
                    val types = o.matchBody.matchArmList.map { Pair(it, it.expr.inferredType) }
                    for ((expr, type) in types) {
                        if (type !is RustUnknownType && type != firstExprType) {
                            holder.registerProblem(expr, "Match arm expression's type ${type.name} does not match expected type ${firstExprType.name}", ProblemHighlightType.GENERIC_ERROR)
                        }
                    }
                }
            }
        }
    }
}
