package org.rust.ide.inspections

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.rust.lang.core.psi.RustFnItem
import org.rust.lang.core.psi.RustVisitor
import org.rust.lang.core.type.RustUnknownType
import org.rust.lang.core.type.inferredType
import org.rust.lang.core.type.resolvedType

class TypeCheckInspection : RustLocalInspectionTool() {
    override fun getDisplayName(): String = "Type Check"

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : RustVisitor() {
            override fun visitFnItem(o: RustFnItem) {
                val blockType = o.block?.expr?.inferredType ?: RustUnknownType(o.manager)
                val retType = o.retType?.type?.resolvedType ?: RustUnknownType(o.manager)
                if (blockType !is RustUnknownType && retType !is RustUnknownType && blockType != retType) {
                    holder.registerProblem(o.block?.expr!!, "Expected type $retType, got $blockType")
                }
            }
        }
    }
}
