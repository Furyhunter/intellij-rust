package org.rust.lang.core.type

import com.intellij.psi.PsiManager
import org.rust.lang.core.psi.RustImplItem

data class RustTupleResolvedType(val types: List<RustResolvedType>) : RustResolvedType {
    override val inheritedImpls: Collection<RustImplItem>
        get() = emptyList()

    val arity: Int
        get() = types.size
}
