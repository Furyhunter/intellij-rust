package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.util.PsiTreeUtil
import org.rust.ide.icons.RustIcons
import org.rust.lang.core.psi.*
import org.rust.lang.core.psi.impl.RustItemImpl
import org.rust.lang.core.stubs.RustItemStub
import javax.swing.Icon

abstract class RustStructItemImplMixin : RustItemImpl, RustStructItem {

    constructor(node: ASTNode) : super(node)

    constructor(stub: RustItemStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override val declarations: Collection<RustDeclaringElement> get() = genericParams.typeParamList

    override val boundElements: Collection<RustNamedElement>
        get() {
            val methods: List<RustNamedElement> = PsiTreeUtil.findChildrenOfType(this, RustImplMethodMember::class.java).toList()
            val consts: List<RustNamedElement> = PsiTreeUtil.findChildrenOfType(this, RustImplConstMember::class.java).toList()
            val assocTypes: List<RustNamedElement> = PsiTreeUtil.findChildrenOfType(this, RustImplTypeMember::class.java).toList()
            return methods + consts + assocTypes + this
        }

    override fun getIcon(flags: Int): Icon =
        iconWithVisibility(flags, RustIcons.STRUCT)
}
