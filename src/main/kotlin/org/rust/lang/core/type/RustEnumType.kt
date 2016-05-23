package org.rust.lang.core.type

import org.rust.lang.core.psi.RustEnumItem
import org.rust.lang.core.psi.RustImplItem
import org.rust.lang.core.psi.RustMod
import org.rust.lang.core.psi.util.parentOfType

class RustEnumType(val enum: RustEnumItem) : RustResolvedTypeBase(enum.manager) {
    override val inheritedImplsInner: Collection<RustImplItem>
        get() {
            val potentialImpls = enum.parentOfType<RustMod>()?.items.orEmpty().filterIsInstance<RustImplItem>()
            return potentialImpls.filter { impl ->
                impl.traitRef == null && (impl.type?.resolvedType == this)
            }
        }

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as RustEnumType

        if (enum != other.enum) return false

        return true
    }

    override fun hashCode(): Int{
        return enum.hashCode()
    }

    override fun toString(): String{
        return "<enum type $enum>"
    }
}
