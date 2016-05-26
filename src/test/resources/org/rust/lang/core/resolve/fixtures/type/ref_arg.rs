struct S;

impl S {
    fn my_test1(&self) { }

    fn my_test2(&self, a: &S) {
        a.<caret>my_test1();
    }
}
