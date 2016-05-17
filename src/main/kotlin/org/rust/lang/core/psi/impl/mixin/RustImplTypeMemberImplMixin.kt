package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import org.rust.lang.core.psi.RustImplTypeMember
import org.rust.lang.core.psi.RustNamedElement
import org.rust.lang.core.psi.impl.RustNamedElementImpl

abstract class RustImplTypeMemberImplMixin(node: ASTNode) : RustNamedElementImpl(node)
                                                          , RustImplTypeMember {
    override val boundElements: Collection<RustNamedElement>
        get() = listOf(this)
}
