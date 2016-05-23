package org.rust.lang.core.type

import org.rust.lang.core.psi.*
import org.rust.lang.core.psi.util.parentOfType

data class RustStructType(val struct: RustStructItem) : RustResolvedType {
    override val inheritedImpls: Collection<RustImplItem> by lazy {
        struct.parentOfType<RustMod>()?.items.orEmpty()
            .filterIsInstance<RustImplItem>()
            .filter { it.traitRef == null && (it.type?.resolvedType == this) }
    }

    val fields: Collection<RustStructDeclField>
        get() {
            return struct.structDeclArgs?.structDeclFieldList ?: emptyList()
        }

    override fun toString(): String = "<struct type $struct>"

}
