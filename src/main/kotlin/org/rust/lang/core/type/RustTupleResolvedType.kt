package org.rust.lang.core.type

import com.intellij.psi.PsiManager
import org.rust.lang.core.psi.RustImplItem

class RustTupleResolvedType(val types: List<RustResolvedType>, manager: PsiManager) : RustResolvedTypeBase(manager) {
    override val inheritedImplsInner: Collection<RustImplItem>
        get() = emptyList()

    val arity: Int
        get() = types.size

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as RustTupleResolvedType

        if (types != other.types) return false

        return true
    }

    override fun hashCode(): Int{
        return types.hashCode()
    }

    override fun toString(): String = "<tuple type ($types)>"
}
