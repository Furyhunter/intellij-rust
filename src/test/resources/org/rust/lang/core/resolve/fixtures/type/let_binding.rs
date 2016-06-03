struct S;

impl S {
    fn test(self) { }
}

fn main() {
    let s = S;
    s.<caret>test();
}
