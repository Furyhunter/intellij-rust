package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiIdentifier
import com.intellij.util.IncorrectOperationException
import org.rust.lang.core.psi.RustMethodCallExpr
import org.rust.lang.core.psi.RustNamedElement
import org.rust.lang.core.psi.impl.RustExprImpl
import org.rust.lang.core.psi.util.childOfType
import org.rust.lang.core.resolve.ref.RustMethodCallReferenceImpl
import org.rust.lang.core.resolve.ref.RustReference

abstract class RustMethodCallExprImplMixin(node: ASTNode?) : RustExprImpl(node), RustMethodCallExpr {
    override fun getReference(): RustReference = RustMethodCallReferenceImpl(this)

    override fun getName(): String? {
        return identifier?.text
    }

    override fun setName(name: String): PsiElement? {
        throw IncorrectOperationException()
    }
}
