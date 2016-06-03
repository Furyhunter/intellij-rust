struct S(i32);

impl S {
    fn test(self) { }
}

fn main() {
    (S).<caret>test();
}
