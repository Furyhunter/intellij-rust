package org.rust.lang.core.type

import com.intellij.psi.PsiManager
import org.rust.lang.core.psi.RustImplItem

open class RustPrimitiveResolvedType(val primitiveName: String, manager: PsiManager) : RustResolvedTypeBase(manager) {
    override val inheritedImplsInner: Collection<RustImplItem>
        get() = emptyList()

    override fun getName(): String? = primitiveName

    override fun toString(): String = "<primitive type $primitiveName>"

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is RustPrimitiveResolvedType) return false

        if (primitiveName != other.primitiveName) return false

        return true
    }

    override fun hashCode(): Int{
        return primitiveName.hashCode()
    }

}
