struct S;

type S2 = S;

impl S {
    fn test(self) { }
}

fn main() {
    (S2).<caret>test()
}
