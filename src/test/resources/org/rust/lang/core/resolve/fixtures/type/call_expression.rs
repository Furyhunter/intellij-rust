struct S;

impl S {
    fn test(self) { }
}

fn hello() -> S { S }

fn main() {
    hello().<caret>test()
}
