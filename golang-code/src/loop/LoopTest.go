package loop

import "fmt"

func LoopUnite() {
	var y int
	a := 10
	for y = 1; y < 10; y++ {
		fmt.Println(y)
	}
	println(":=的作用是自动判断类型并赋值，不需要var定义,a=", a)
}
