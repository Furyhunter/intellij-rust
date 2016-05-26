package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import org.rust.lang.core.psi.RustFieldExpr
import org.rust.lang.core.psi.impl.RustExprImpl
import org.rust.lang.core.resolve.ref.RustReference

abstract class RustFieldExprImplMixin(node: ASTNode?) : RustExprImpl(node), RustFieldExpr {
    override fun getReference(): RustReference? = null
}
