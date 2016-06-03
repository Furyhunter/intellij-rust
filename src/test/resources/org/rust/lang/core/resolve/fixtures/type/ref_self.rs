struct S;

impl S {
    fn test1(&self) {

    }

    fn test2(&self) {
        self.<caret>test1();
    }
}
