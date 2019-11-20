package job

import (
	"fmt"
	"github.com/robfig/cron"
)
import "log"

func StartJob() {
	i := 0
	c := cron.New()
	spec := "0/5 * * * * ?"
	c.AddFunc(spec, func() {
		i++
		fmt.Printf("hello,%d", i)
		log.Printf("cron running:%d", i)
	})
	c.Start()

	select {}
}
