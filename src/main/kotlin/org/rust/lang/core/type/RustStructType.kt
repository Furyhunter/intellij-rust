package org.rust.lang.core.type

import org.rust.lang.core.psi.RustFieldDecl
import org.rust.lang.core.psi.RustImplItem
import org.rust.lang.core.psi.RustMod
import org.rust.lang.core.psi.RustStructItem
import org.rust.lang.core.psi.util.parentOfType

data class RustStructType(val struct: RustStructItem) : RustResolvedType {
    override val inheritedImpls: Collection<RustImplItem> by lazy {
        struct.parentOfType<RustMod>()?.items.orEmpty()
            .filterIsInstance<RustImplItem>()
            .filter { it.traitRef == null && (it.type?.resolvedType == this) }
    }

    val fields: Collection<RustFieldDecl>
        get() {
            return struct.structDeclArgs?.fieldDeclList ?: emptyList()
        }

    override fun toString(): String = "<struct type $struct>"

    override val name: String = struct.name ?: ""
}
