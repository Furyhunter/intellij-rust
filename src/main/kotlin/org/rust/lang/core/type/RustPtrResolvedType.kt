package org.rust.lang.core.type

import org.rust.lang.core.psi.RustImplItem

data class RustPtrResolvedType(val mut: Boolean, val type: RustResolvedType) : RustResolvedType {
    override val inheritedImpls: Collection<RustImplItem>
        get() = type.inheritedImpls
}
