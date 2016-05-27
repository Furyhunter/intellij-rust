package org.rust.lang.core.type

import org.rust.lang.core.psi.RustImplItem

object RustUnitResolvedType : RustResolvedType {
    override val inheritedImpls: Collection<RustImplItem>
        get() = emptyList()
}
