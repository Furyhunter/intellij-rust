package org.rust.lang.core.type

import org.rust.lang.core.psi.RustEnumItem
import org.rust.lang.core.psi.RustImplItem
import org.rust.lang.core.psi.RustMod
import org.rust.lang.core.psi.util.parentOfType

data class RustEnumType(val enum: RustEnumItem) : RustResolvedType {
    override val inheritedImpls: Collection<RustImplItem>
        get() {
            val potentialImpls = enum.parentOfType<RustMod>()?.items.orEmpty().filterIsInstance<RustImplItem>()
            return potentialImpls.filter { impl ->
                impl.traitRef == null && (impl.type?.resolvedType == this)
            }
        }
}
