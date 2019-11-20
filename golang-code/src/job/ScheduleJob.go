package job

import (
	"github.com/robfig/cron"
	log "github.com/sirupsen/logrus"
)

type Hello struct {
	Str string
}

func (h Hello) Run() {
	log.Println(h.Str)
}

func StartJob() {
	log.Info("开始定时任务")
	c := cron.New()
	h := Hello{"I Love You!"}
	spec := "*/2 * * * * ?"
	c.AddJob(spec, h)
	c.Start()
	select {}
}
