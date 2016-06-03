enum E {
    V1,
    V2
}

impl E {
    fn test(self) { }
}

fn main() {
    E::V1.<caret>test();
}
