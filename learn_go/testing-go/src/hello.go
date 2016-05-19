package main

import (
	"fmt"
	"runtime"
	"time"
)

type Salutation struct {
	Name     string
	greeting string
}

type Renameable interface {
	Rename(name string)
}

type Printer func(string)

func renameToFrog(rename Renameable) {
	rename.Rename("Testtttt")
}

func (s *Salutation) Rename(name string) {
	s.Name = name
	s.greeting = name
}

func (s Salutation) Greet(do Printer) {
	fmt.Println("s = ", s)
	do(s.greeting)
	do(s.Name)
}
func Print(s string) {
	fmt.Print(s)
}
func Println(s string) {
	fmt.Println(s)
}

func PrintExtra(extra string) Printer {
	return func(s string) {
		println(s + " " + extra)
	}
}

type CourseMeta struct {
	Author string
	Level  string
	Rating float64
}

func main() {

	//var GoFuntanMental CourseMeta
	GoFuntanMental := new(CourseMeta)
	GoFuntanMental1 := CourseMeta{
		Author: "Mohan",
		Rating: 23.23,
	}
	GoFuntanMental1.Level = "Advanced"
	fmt.Println("GoFuntanMental = ", GoFuntanMental)
	fmt.Println("GoFuntanMental1 = ", GoFuntanMental1)

	var s = Salutation{greeting: "Hi", Name: "Mohan"}

	renameToFrog(&s)

	s.Greet(PrintExtra("???"))
	PrintType(23)

	var slice []int
	slice = make([]int, 0)

	slice = append(slice, 1)
	slice = append(slice, 10)
	slice = append(slice, 100)
	fmt.Println("slice = ", slice)

}

func PrintType(x interface{}) {
	switch  x.(type) {
	case int:
		fmt.Println("Int")
	default:
		fmt.Println("Default")
	}

}

func test() string {
	fmt.Println("Inside test")
	return "Inside test"
}

func channelExample() {
	runtime.GOMAXPROCS(8)
	ch := make(chan string)
	doneCh := make(chan bool)
	go abcGen(ch)
	go print(ch, doneCh)
	//time.Sleep(2 * time.Second)
	fmt.Println("<-doneCh = ", <-doneCh)

}

func abcGen(ch chan string) {
	for l := byte('a'); l <= byte('z'); l++ {
		ch <- string(l)
	}

	close(ch)
	println("Ch closed")
}

func print(ch chan string, doneCh chan bool) {
	for char := range ch {
		time.Sleep(100 * time.Millisecond)
		fmt.Println("char = ", char)
	}

	println("Printer is done")
	doneCh <- false
}