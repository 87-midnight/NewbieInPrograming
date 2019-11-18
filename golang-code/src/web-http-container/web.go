package main

import (
	"fmt"
	"net/http"
	"strings"
	"log"
)
func dispatcher(w http.ResponseWriter, r *http.Request) {

	r.ParseForm()

	str := make([]string, 10)

	strings.Join(str, r.URL.Path)

	strings.Join(str, "\n")



	for k, v := range r.Form {

	str = append(str, k)

	str = append(str, "=")

	str = append(str, v...)

	str = append(str, "\n")



	fmt.Println("key:", k)

	fmt.Println("val:", strings.Join(v, ""))

	}



	str = append(str, "Hello world ")



	fmt.Fprintln(w, strings.Join(str, " ")) //这个写入到 w 的是输出到客户端的

	}

func main() {

	http.HandleFunc("/", dispatcher)//设置访问的路由

	err := http.ListenAndServe(":9090", nil) //设置监听的端口

	if err != nil {

	log.Fatal("ListenAndServe: ", err)

	}

	}