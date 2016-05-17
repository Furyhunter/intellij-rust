package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.util.PsiTreeUtil
import org.rust.ide.icons.RustIcons
import org.rust.lang.core.psi.*
import org.rust.lang.core.psi.impl.RustItemImpl
import org.rust.lang.core.stubs.RustItemStub
import javax.swing.Icon

abstract class RustImplItemImplMixin : RustItemImpl, RustImplItem {

    constructor(node: ASTNode) : super(node)

    constructor(stub: RustItemStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override val declarations: Collection<RustDeclaringElement> get() = methods + consts + associatedTypes + genericParams.typeParamList

    override val boundElements: Collection<RustNamedElement>
        get() {
            val methods: List<RustNamedElement> = PsiTreeUtil.findChildrenOfType(this, RustImplMethodMember::class.java).toList()
            val consts: List<RustNamedElement> = PsiTreeUtil.findChildrenOfType(this, RustImplConstMember::class.java).toList()
            val assocTypes: List<RustNamedElement> = PsiTreeUtil.findChildrenOfType(this, RustImplTypeMember::class.java).toList()
            return methods + consts + assocTypes
        }

    override fun getIcon(flags: Int): Icon = RustIcons.IMPL

    private val methods: List<RustDeclaringElement> get() = PsiTreeUtil.findChildrenOfType(this, RustImplMethodMember::class.java).toList()
    private val consts: List<RustDeclaringElement> get() = PsiTreeUtil.findChildrenOfType(this, RustImplConstMember::class.java).toList()
    private val associatedTypes: List<RustDeclaringElement> get() = PsiTreeUtil.findChildrenOfType(this, RustImplTypeMember::class.java).toList()
    private val macros: List<RustImplMacroMember> get() = PsiTreeUtil.findChildrenOfType(this, RustImplMacroMember::class.java).toList()
}
