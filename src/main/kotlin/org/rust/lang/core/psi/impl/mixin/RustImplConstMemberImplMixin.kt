package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import org.rust.lang.core.psi.RustImplConstMember
import org.rust.lang.core.psi.RustNamedElement
import org.rust.lang.core.psi.impl.RustNamedElementImpl

abstract class RustImplConstMemberImplMixin(node: ASTNode) : RustNamedElementImpl(node)
                                                           , RustImplConstMember {
    override val boundElements: Collection<RustNamedElement>
        get() = listOf(this)
}
