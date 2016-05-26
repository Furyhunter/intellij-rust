struct S;

impl S {
    fn test1(&mut self) {

    }

    fn test2(&mut self) {
        self.<caret>test1();
    }
}
