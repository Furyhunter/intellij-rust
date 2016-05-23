package org.rust.lang.core.type

import org.rust.lang.core.psi.RustImplItem
import org.rust.lang.core.psi.RustTypeParam

class RustParameterizedResolvedType(val element: RustTypeParam) : RustResolvedTypeBase(element.manager) {
    override val inheritedImplsInner: Collection<RustImplItem>
        get() = emptyList()

    override fun toString(): String = "<type parameter type $element>"

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is RustParameterizedResolvedType) return false

        if (element != other.element) return false

        return true
    }

    override fun hashCode(): Int{
        return element.hashCode()
    }


}
