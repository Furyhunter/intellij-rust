enum E {
    V1(i32),
    V2
}

impl E {
    fn test(self) { }
}

fn main() {
    E::V1(4).<caret>test();
}
