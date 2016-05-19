package main

import (
	"fmt"
	"time"
)

func main() {
	genChannel := make(chan int)
	go generate(genChannel)

	for {
		number := <-genChannel
		fmt.Println("prime = ", number)
		outChan := make(chan int)
		go filter(genChannel, outChan, number)
		genChannel = outChan
	}

	//fmt.Scanln()
}

func generate(ch chan int) {
	for i := 2;; i++ {
		//fmt.Println("generated = ", i)
		time.Sleep(100 * time.Millisecond)
		ch <- i
	}
}

func filter(in, out chan int, prime int) {
	for {
		i := <-in
		fmt.Println("i from inchan = ", i)
		if i % prime != 0 {
			out <- i
		}
	}
}