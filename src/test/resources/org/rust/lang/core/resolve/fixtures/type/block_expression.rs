struct S;

impl S {
    fn test(self) { }
}

fn main() {
    {
        let a = 4 + 3;
        S
    }.<caret>test();
}
