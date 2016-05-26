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
                    val type = impl?.type?.resolvedType ?: RustUnknownType
                    when {
                        type is RustUnknownType -> type
                        target.and != null      -> RustRefResolvedType(target.mut != null, type)
                        else                    -> type
                    }
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
                is RustTypeItem -> {
                    // Aliased type w/ unit constructor expression
                    RustTypeAliasType(target)
                }
                is RustConstItem  -> target.type.resolvedType
                is RustStaticItem -> target.type.resolvedType
                else -> RustUnknownType
            }
        }
        is RustStructExpr -> {
            val resolvedElement = path.reference.resolve()
            when (resolvedElement) {
                is RustStructItem  -> RustStructType(resolvedElement)
                is RustEnumVariant -> resolvedElement.parentOfType<RustEnumItem>()?.let { RustEnumType(it) } ?: RustUnknownType
                is RustTypeItem    -> RustTypeAliasType(resolvedElement)
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
                is RustTypeItem    -> RustTypeAliasType(resolvedElement)
                else               -> RustUnknownType
            }
        }
        is RustMethodCallExpr -> {
            val e = expr
            val eType = e.inferredType
            eType.nonStaticMethods.find { it.name == identifier?.text }?.retType?.type?.resolvedType ?: RustUnknownType
        }
        is RustParenExpr -> {
            expr.inferredType
        }
        is RustBlockExpr -> {
            block?.expr?.inferredType ?: RustUnknownType
        }
        is RustFieldExpr -> {
            val e = expr
            val eType = e.inferredType
            when (eType) {
                is RustStructType -> eType.fields.find { it.name == identifier?.text }?.type?.resolvedType ?: RustUnknownType
                else -> RustUnknownType
            }
        }
        is RustTupleExpr -> {
            RustTupleResolvedType(exprList.map { it.inferredType }, manager)
        }
        is RustUnaryExpr -> {
            // Depends on the operator being used.
            // Will have to also look at the traits that implement unary operations and see what types they yield.
            when {
                and != null -> RustRefResolvedType(mut != null, expr?.inferredType ?: RustUnknownType)
                else        -> RustUnknownType
            }
        }
        is RustLitExpr -> {
            val inferredName = when {
                integerLiteral != null            -> integerLiteralNames.mapNotNull { if (text.endsWith(it)) it else null }.firstOrNull() ?: "i32"
                floatLiteral   != null            -> floatLiteralNames.mapNotNull { if (text.endsWith(it)) it else null }.firstOrNull() ?: "f64"
                charLiteral != null               -> "char"
                byteLiteral != null               -> "u8"
                `true` != null || `false` != null -> "bool"
                stringLiteral != null             -> "str" // special case; it's &'static str
                else                              -> null
            }
            if (inferredName != null) RustPrimitiveResolvedType(inferredName, manager)
            else                      RustUnknownType
        }
        else -> RustUnknownType
    }
}

private val integerLiteralNames: List<String> = listOf(
    "isize",
    "usize",
    "i8",
    "i16",
    "i32",
    "i64",
    "u8",
    "u16",
    "u32",
    "u64"
)

private val floatLiteralNames: List<String> = listOf(
    "f32",
    "f64"
)

private val literalNames: List<String> = integerLiteralNames + floatLiteralNames + listOf(
    "bool",
    "char",
    "str"
)

val RustType.resolvedType: RustResolvedType by psiCached {
    when (this) {
        is RustPathType -> {
            val p = path
            if (p?.text?.let { literalNames.contains(it) } ?: false) {
                p?.text?.let { RustPrimitiveResolvedType(it, manager) } ?: RustUnknownType
            } else {
                val target = p?.reference?.resolve()
                when (target) {
                    is RustStructItem -> RustStructType(target)
                    is RustEnumItem   -> RustEnumType(target)
                    is RustTypeItem   -> RustTypeAliasType(target)
                    is RustTypeParam  -> RustParameterizedResolvedType(target)
                    else -> RustUnknownType
                }
            }
        }
        is RustRefType   -> RustRefResolvedType(mut != null, type?.resolvedType ?: RustUnknownType)
        is RustPtrType   -> RustPtrResolvedType(mut != null, type?.resolvedType ?: RustUnknownType)
        is RustTupleType -> RustTupleResolvedType(typeList.map { it.resolvedType }, manager)
        else -> RustUnknownType
    }
}

