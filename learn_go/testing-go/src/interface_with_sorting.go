package main

import (
	"fmt"
	"sort"
)

type Person struct {
	Name string
	Age  int
}

type PersonByAge []Person

func (p PersonByAge) Len() int {
	return len(p)
}

func (p PersonByAge) Swap(i, j int) {
	p[i], p[j] = p[j], p[i]
}

func (p PersonByAge) Less(i, j int) bool {
	return p[i].Age < p[j].Age
}

func main() {
	people := []Person{
		{Name: "Person1", Age: 25, },
		{Name: "Person2", Age: 21, },
		{Name: "Person3", Age: 15, },
		{Name: "Person4", Age: 45, },
		{Name: "Person5", Age: 25, },
	}

	fmt.Println(people)
	sort.Sort(PersonByAge(people))
	fmt.Println(people)
}

