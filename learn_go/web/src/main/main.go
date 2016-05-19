package main

import (
	"net/http"
	"io/ioutil"
	"fmt"
)

func main() {
	http.Handle("/", new(MyHandler))

	http.ListenAndServe(":8080", nil)
}

type MyHandler struct {
	http.Handler
}

func (this *MyHandler) ServeHTTP(w http.ResponseWriter, req *http.Request) {
	path := "public/" + req.URL.Path
	fmt.Println("path :" + path)
	data, err := ioutil.ReadFile(path)

	fmt.Println("data + " + string(data))
	fmt.Errorf("%s", err)

	if (err == nil) {
		w.Write(data)
	} else {
		w.WriteHeader(404)
		w.Write([]byte("404 - " + http.StatusText(404)))
	}

}