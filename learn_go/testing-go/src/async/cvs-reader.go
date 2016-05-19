package main

import (
	"os"
	"io/ioutil"
	"encoding/csv"
	"fmt"
	"strings"
	"strconv"
)

func main() {
	file, _ := os.Open("/Users/mohanraj.nagasamy/IdeaProjects/testing-go/src/async/csv01.csv")
	defer file.Close()

	data, _ := ioutil.ReadAll(file)

	cvsReader := csv.NewReader(strings.NewReader(string(data)))
	records, _ := cvsReader.ReadAll()

	for _, r := range records {
		invoice := new(InvoiceRecord)
		invoice.Name = r[0]
		value, _ := strconv.Atoi(r[1])
		fmt.Println("value = ", value)
		invoice.Price = float64(value)

		fmt.Println("invoice = ", invoice)
	}
}

type InvoiceRecord struct {
	Name  string
	Price float64
}