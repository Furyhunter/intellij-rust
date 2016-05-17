package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException
import org.rust.lang.core.psi.RustNamedElement
import org.rust.lang.core.psi.RustTraitConstMember
import org.rust.lang.core.psi.impl.RustCompositeElementImpl

abstract class RustTraitConstMemberImplMixin(node: ASTNode) : RustCompositeElementImpl(node)
                                                            , RustTraitConstMember {
    override val boundElements: Collection<RustNamedElement>
        get() = listOf(this)

    override fun setName(name: String): PsiElement? {
        throw IncorrectOperationException()
    }
}
