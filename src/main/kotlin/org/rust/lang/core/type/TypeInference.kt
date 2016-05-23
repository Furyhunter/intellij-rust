package org.rust.lang.core.type

import org.rust.lang.core.psi.*
import org.rust.lang.core.psi.util.parentOfType
import org.rust.lang.utils.psiCached

val RustExpr.inferredType: RustResolvedType by psiCached {
    when (this) {
        is RustPathExpr -> {
            val target = path.reference.resolve()
            when (target) {
                is RustSelfArgument -> {
                    val impl = target.parentOfType<RustImplItem>()
                    impl?.type?.resolvedType ?: RustUnknownType
                }
                is RustPatBinding -> {
                    val targetParent = target.parent
                    when (targetParent) {
                        is RustLetDecl   -> targetParent.type?.resolvedType ?: targetParent.expr?.inferredType ?: RustUnknownType
                        is RustParameter -> targetParent.type?.resolvedType ?: RustUnknownType
                        else             -> RustUnknownType
                    }
                }
                is RustStructItem -> {
                    // Unit struct type
                    RustStructType(target)
                }
                is RustEnumVariant -> {
                    // Unit variant of enum type
                    target.parentOfType<RustEnumItem>()?.let { RustEnumType(it) } ?: RustUnknownType
                }
                else -> RustUnknownType
            }
        }
        is RustStructExpr -> {
            val resolvedElement = path.reference.resolve()
            when (resolvedElement) {
                is RustStructItem  -> RustStructType(resolvedElement)
                is RustEnumVariant -> resolvedElement.parentOfType<RustEnumItem>()?.let { RustEnumType(it) } ?: RustUnknownType
                else               -> RustUnknownType
            }
        }
        is RustCallExpr -> {
            val e = expr
            val resolvedElement = when (e) {
                is RustPathExpr -> e.path.reference.resolve()
                else            -> null
            }
            when (resolvedElement) {
                is RustStructItem  -> RustStructType(resolvedElement)
                is RustEnumVariant -> resolvedElement.parentOfType<RustEnumItem>()?.let { RustEnumType(it) } ?: RustUnknownType
                is RustFnItem      -> resolvedElement.retType?.type?.resolvedType ?: RustUnknownType
                else               -> RustUnknownType
            }
        }
        is RustMethodCallExpr -> {
            val m = reference?.resolve()
            when (m) {
                is RustImplMethodMember  -> m.retType?.type?.resolvedType ?: RustUnknownType
                is RustTraitMethodMember -> m.retType?.type?.resolvedType ?: RustUnknownType
                else -> RustUnknownType
            }
        }
        is RustParenExpr -> {
            expr?.inferredType ?: RustUnknownType
        }
        is RustBlockExpr -> {
            block?.expr?.inferredType ?: RustUnknownType
                is RustSelfArgument -> target.parentOfType<RustImplItem>()?.type?.resolvedType ?: RustUnknownType
                else -> RustUnknownType
            }
        }
        else -> RustUnknownType
    }
}

val RustType.resolvedType: RustResolvedType by psiCached {
    when (this) {
        is RustPathType -> {
            val target = path?.reference?.resolve()
            when (target) {
                is RustStructItem -> RustStructType(target)
                else -> RustUnknownType
                is RustEnumItem   -> RustEnumType(target)
            }
        }
        else -> RustUnknownType
    }
}

