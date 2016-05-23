package org.rust.lang.core.type

import org.rust.lang.core.psi.*
import org.rust.lang.core.resolve.ref.RustReference

class RustTypeAliasType(val alias: RustTypeItem) : RustResolvedTypeBase(alias.manager) {
    override val inheritedImplsInner: Collection<RustImplItem>
        get() {
            val ref = typeReference?.resolve()
            val t = when (ref) {
                is RustStructItem -> RustStructType(ref)
                is RustEnumItem   -> RustEnumType(ref)
                is RustTypeItem   -> RustTypeAliasType(ref)
                else -> null
            }
            return t?.inheritedImpls ?: emptyList()
        }

    val typeReference: RustReference?
        get() = (alias.type as? RustPathType)?.path?.reference

    override fun toString(): String = "<type alias type $alias>"

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as RustTypeAliasType

        if (alias != other.alias) return false

        return true
    }

    override fun hashCode(): Int{
        return alias.hashCode()
    }
}
