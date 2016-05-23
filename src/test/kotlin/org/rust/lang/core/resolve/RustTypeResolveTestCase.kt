package org.rust.lang.core.resolve

class RustTypeResolveTestCase : RustResolveTestCaseBase() {
    override val dataPath = "org/rust/lang/core/resolve/fixtures/type"

    fun testMethod() = checkIsBound(atOffset = 27)
    fun testLetBinding() = checkIsBound()
    fun testLetBindingAscripted() = checkIsBound()
    fun testParenExpression() = checkIsBound()
    fun testBlockExpression() = checkIsBound()
    fun testCallExpression() = checkIsBound()
    fun testFieldExpression() = checkIsBound()
    fun testTypeAlias() = checkIsBound()

    fun testUnitStructExpression() = checkIsBound()
    fun testTupleStructExpression() = checkIsBound()
    fun testDeclStructExpression() = checkIsBound()

    fun testEnumUnitVariantExpression() = checkIsBound()
    fun testEnumTupleVariantExpression() = checkIsBound()
    fun testEnumDeclVariantExpression() = checkIsBound()

    // The following tests check that the element is _not_ bound
    // because as of their creation, the features weren't implemented.
    // So when the feature is implemented correctly, these tests will
    // fail -- switch them back to checkIsBound.
    fun testTupleExpression() = checkIsUnbound()
}
