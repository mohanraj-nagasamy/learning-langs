package main

import (
	"fmt"
	"time"
	"sync"
	"runtime"
)

func waitGroupExample() {
	fmt.Println("Hello go routines")
	var waitGroup sync.WaitGroup
	waitGroup.Add(2)

	go func() {
		defer waitGroup.Done()
		time.Sleep(5 * time.Second)
		fmt.Println("1) Hello")
	}()

	go func() {
		defer waitGroup.Done()
		fmt.Println("2) Hello")
	}()
	waitGroup.Wait()

}

func main() {
	runtime.GOMAXPROCS(4)

	start := time.Now()
	ch := make(chan int, 10)
	done := make(chan bool)

	go func() {
		i := 0
		for i <= 10 {
			ch <- i
			fmt.Println("Putting i=", i)
			i ++
		}
		close(ch)
	}()

	go func() {
		doneCounter := 0

		for out := range ch {
			doneCounter ++
			go func(i int) {
				fmt.Println(doneCounter, ") Getting i = ", i)
				time.Sleep(1 * time.Second)
				doneCounter --
			}(out)

		}

		for doneCounter > 0 {
			fmt.Println("doneCounter = ", doneCounter)
			time.Sleep(100 * time.Millisecond)
		}

		fmt.Println("Outer = ", doneCounter)
		done <- true
	}()

	<-done
	fmt.Println("Total time = ", (time.Since(start)))
}
