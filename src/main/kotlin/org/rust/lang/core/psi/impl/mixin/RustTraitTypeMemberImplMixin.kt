package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException
import org.rust.lang.core.psi.RustNamedElement
import org.rust.lang.core.psi.RustTraitTypeMember
import org.rust.lang.core.psi.impl.RustCompositeElementImpl

abstract class RustTraitTypeMemberImplMixin(node: ASTNode) : RustCompositeElementImpl(node)
                                                           , RustTraitTypeMember {
    override val boundElements: Collection<RustNamedElement>
        get() = listOf(this)

    override fun setName(name: String): PsiElement? {
        throw IncorrectOperationException()
    }
}
