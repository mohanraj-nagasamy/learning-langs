package main

import (
	"fmt"
	"time"
	"runtime"
)

func main() {
	runtime.GOMAXPROCS(2)
	go func() {
		for i := 0; i < 1000; i++ {
			fmt.Println(i, ") Hello")
			time.Sleep(10 * time.Millisecond)
		}
	}()

	go func() {
		for i := 0; i < 1000; i++ {
			fmt.Println(i, ") Go")
			time.Sleep(10 * time.Millisecond)
		}
	}()

	time.Sleep(10 * time.Second)
}
