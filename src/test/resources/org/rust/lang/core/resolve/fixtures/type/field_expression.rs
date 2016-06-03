struct S1 {
    a: S2
}

struct S2;

impl S2 {
    fn test(self) { }
}

fn main() {
    S1 { a: S2 }.a.<caret>test()
}
