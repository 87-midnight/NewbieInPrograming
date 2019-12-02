package main

import "fmt"

const name = "serial_calculate"

func plus(a int, b int) int {
	return a + b
}

func minus(a int, b int) int {
	return a - b
}

func multiply(a int, b int) int {
	return a * b
}

func divide(a int, b int) int {
	return a / b
}

func pointer(a *int, b *int) {
	*a, *b = *b, *a
}

func main() {
	a := 22
	b := 18
	fmt.Println(plus(a, b))
	fmt.Println(minus(a, b))
	fmt.Println(multiply(a, b))
	fmt.Println(divide(a, b))
	fmt.Printf("交换前a=%d,b=%d\n", a, b)
	pointer(&a, &b)
	fmt.Printf("交换后a=%d,b=%d\n", a, b)
}
