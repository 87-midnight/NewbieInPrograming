package main

import "./helloWorld"
import "./loop"
import "./job"
import "./entity"

func main() {
	user := entity.User{UserId: 1, Username: "test", Gender: "female"}
	user.InsertUser()
	helloWorld.PrintWellcome()
	helloWorld.Start()
	loop.LoopUnite()
	job.StartJob()

}
