extern crate actix;
use actix::{msgs, Actor, Addr, Arbiter, Context, System};

fn main() {
    let system = System::new("test");
    system.run();
    println!("Hello, world!");
}
