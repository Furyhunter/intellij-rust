package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import org.rust.ide.icons.RustIcons
import org.rust.ide.icons.addStaticMark
import org.rust.lang.core.psi.*
import org.rust.lang.core.psi.impl.RustNamedElementImpl
import javax.swing.Icon

abstract class RustImplMethodImplMixinMember(node: ASTNode) : RustNamedElementImpl(node)
                                                            , RustImplMethodMember {

    override val declarations: Collection<RustDeclaringElement> get() {
        val params = parameters ?: return emptyList()
        return listOfNotNull(params.selfArgument) + params.parameterList.orEmpty() + genericParams.typeParamList
    }

    override fun getIcon(flags: Int): Icon? {
        var icon = RustIcons.METHOD
        if (isStatic) {
            icon = icon.addStaticMark()
        }
        return iconWithVisibility(flags, icon)
    }

    override val innerAttrList: List<RustInnerAttr>
        get() = block?.innerAttrList.orEmpty()

    override val boundElements: Collection<RustNamedElement>
        get() = listOf(this)
}

val RustImplMethodMember.isStatic: Boolean get() = parameters?.selfArgument == null
