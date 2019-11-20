package loop

import (
	log "github.com/sirupsen/logrus"
)

func LoopUnite() {
	var y int
	a := 10
	for y = 1; y < 10; y++ {
		log.Info("hello,%d", y)
	}
	println(":=的作用是自动判断类型并赋值，不需要var定义,a=", a)
}
