package org.rust.lang.core.resolve.ref

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import org.rust.lang.core.psi.RustFieldDecl
import org.rust.lang.core.type.resolvedType

fun RustFieldDecl.createLookupElement(): LookupElement {
    return LookupElementBuilder.create(this)
        .withTypeText(type?.resolvedType?.toString() ?: "")
}
