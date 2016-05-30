package org.rust.lang.core.type

import com.intellij.psi.PsiManager
import org.rust.lang.core.psi.RustImplItem

data class RustPrimitiveResolvedType(val primitiveName: String) : RustResolvedType {
    override val inheritedImpls: Collection<RustImplItem>
        get() = emptyList()

    override val name: String = primitiveName
}
