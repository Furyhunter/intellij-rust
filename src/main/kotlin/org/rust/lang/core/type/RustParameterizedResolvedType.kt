package org.rust.lang.core.type

import org.rust.lang.core.psi.RustImplItem
import org.rust.lang.core.psi.RustTypeParam

data class RustParameterizedResolvedType(val element: RustTypeParam) : RustResolvedType {
    override val inheritedImpls: Collection<RustImplItem>
        get() = emptyList()

    override val name: String = element.name ?: ""
}
