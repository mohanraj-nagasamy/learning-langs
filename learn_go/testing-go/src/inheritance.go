package main

import "fmt"

type Person struct {
	Name     string
	JobTitle string
	ShoeSize float32
}

func (p Person) SayHello() {
	fmt.Println("Hello! I'm " + p.Name)
}

func (p Person) GotJob() {
	fmt.Println("Im a " + p.JobTitle + "!")
}

type Doctor struct {
	Person
	Degree string
}

func (d Doctor) SayHello() {
	fmt.Println("Hello! I'm " + d.Name + ", " + d.Degree)
}

func main() {
	p := Person{Name: "Mohan", JobTitle: "s/w eng"}
	p.SayHello()
	p.GotJob()

	doc := Doctor{p, "Msc (cs)"}
	doc.SayHello()
	doc.GotJob()
}