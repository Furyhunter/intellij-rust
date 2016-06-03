enum E {
    V1 { a: i32 },
    V2
}

impl E {
    fn test(self) { }
}

fn main() {
    E::V1 { a: 4 }.<caret>test();
}
