package main

import (
	"net/http"
	"io/ioutil"
	"encoding/xml"
	"fmt"
)

func main() {
	resp, _ := http.Get("http://dev.markitondemand.com/MODApis/Api/v2/Quote/xml?symbol=AAPL")
	defer resp.Body.Close()

	body, _ := ioutil.ReadAll(resp.Body)

	quote := new(Quote)
	xml.Unmarshal(body, &quote)

	fmt.Println("Name: " + quote.Name + " " + "Price: " + quote.LastPrice)
}

type Quote struct {
	Name      string
	LastPrice string
}