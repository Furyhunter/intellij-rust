struct S {
    a: i32
}

impl S {
    fn test(self) { }
}

fn main() {
    S { a: 3 }.<caret>test();
}
