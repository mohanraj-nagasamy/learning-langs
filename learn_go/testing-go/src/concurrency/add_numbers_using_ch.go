package main

import "fmt"

func sum(n []int, ch chan int) {
	sum := 0
	for _, value := range n {
		sum += value
	}
	ch <- sum
}

func main() {
	fmt.Println("Main.")
	s := []int{7, 2, 8, -9, 4, 0}
	ch := make(chan int)
	l := len(s)
	go sum(s[:(l / 2)], ch)
	go sum(s[(l / 2):], ch)
	x, y := <-ch, <-ch

	fmt.Println("x = ", x)
	fmt.Println("y = ", y)
	fmt.Println("(x+ y) = ", (x + y))

	fmt.Println("fib(8) = ", fib(80%))
}

var fibCache []int = make([]int, 200)

func fib(n int) int {
	fmt.Println("n = ", n)
	if n <= 1 {
		fibCache[n] = 1
	}
	fmt.Println("fibCache = ", fibCache)
	if fibCache[n] == 0 {
		fibCache[n] = fib(n - 1) + fib(n - 2)
	}
	fmt.Println("fibCache = ", fibCache)
	return fibCache[n]
}