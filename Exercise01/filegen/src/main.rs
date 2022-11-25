use std::env;
use std::fs::File;
use std::io::{BufWriter, Write};
use std::path::Path;

fn main() {
    let args: Vec<String> = env::args().collect();
    
    let size: u64 = args[1].trim().parse().expect("{args[1]} is not a valid number!");
    dbg!(size);
    let size = size * 1000u64;

    let x: i32 = args[2].trim().parse().expect("{args[1]} is not a valid number!");
    let y: i32 = args[3].trim().parse().expect("{args[1]} is not a valid number!");

    let path = Path::new(&args[4]);
    let display = path.display();

    let buf_size = 130000usize;


    println!("Set buffer size to {} bytes", buf_size);

    let n = size / 4u64;

    let file = match File::create(&path) {
        Err(why) => panic!("Couldn't create {}: {}", display, why),
        Ok(file) => file,
    };

    let mut buffer = BufWriter::with_capacity(buf_size as usize, file);

    for _ in 0..n {
        let rand_num = fastrand::i32(x..y).to_be_bytes();
        buffer.write(&rand_num).expect("Write error.");
    }
    match buffer.flush() {
        Err(why) => panic!("Couldn't write to {}: {}", display, why),
        Ok(_) => println!("{} random numbers written into {}", n, display),
    }
}
