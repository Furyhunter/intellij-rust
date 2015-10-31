package org.rust.lang.icons

import com.intellij.ide.IconProvider
import com.intellij.openapi.util.Iconable
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiUtil
import com.intellij.ui.RowIcon
import com.intellij.util.PlatformIcons
import com.intellij.util.VisibilityIcons
import com.intellij.util.ui.EmptyIcon
import org.rust.lang.core.psi.RustEnumItem
import org.rust.lang.core.psi.RustItem
import org.rust.lang.core.psi.RustStructDeclField
import org.rust.lang.core.psi.RustStructItem
import org.rust.lang.core.psi.impl.*
import javax.swing.Icon

class RustIconProvider: IconProvider() {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        return when (element) {
            is RustEnumItemImpl -> getEnumIcon(element, flags)
            is RustStructItemImpl -> getStructIcon(element, flags)
            is RustStructDeclField -> getStructDeclFieldIcon(element, flags)
            is RustFnItemImpl -> RustIcons.FUNCTION
            is RustImplMethodImpl -> RustIcons.METHOD
            else -> null
        }
    }

    private fun getEnumIcon(element: RustEnumItem, flags: Int): Icon? {
        if ((flags and Iconable.ICON_FLAG_VISIBILITY) == 0)
            return RustIcons.ENUM;

        val parent = element.parent?.parent as RustItem?
        return addVisibilityIcon(RustIcons.ENUM, parent?.pub)
    }

    private fun getStructIcon(element: RustStructItem, flags: Int): Icon? {
        if ((flags and Iconable.ICON_FLAG_VISIBILITY) == 0)
            return RustIcons.CLASS;

        val parent = element.parent?.parent as RustItem?
        return addVisibilityIcon(RustIcons.CLASS, parent?.pub)
    }

    private fun getStructDeclFieldIcon(element: RustStructDeclField, flags: Int): Icon? {
        if ((flags and Iconable.ICON_FLAG_VISIBILITY) == 0)
            return RustIcons.FIELD;

        return addVisibilityIcon(RustIcons.FIELD, element.pub)
    }

    private fun addVisibilityIcon(baseIcon: Icon, pub: PsiElement?): RowIcon {
        return addVisibilityIcon(baseIcon, pub != null)
    }

    private fun addVisibilityIcon(baseIcon: Icon, pub: Boolean): RowIcon {
        val visibility = if (pub) PsiUtil.ACCESS_LEVEL_PUBLIC else PsiUtil.ACCESS_LEVEL_PRIVATE
        val icon = RowIcon(baseIcon, EmptyIcon.create(PlatformIcons.PUBLIC_ICON))
        VisibilityIcons.setVisibilityIcon(visibility, icon);
        return icon;
    }
}
