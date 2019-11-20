package main

import "./helloWorld"
import "./loop"
import "./job"

func main() {
	helloWorld.PrintWellcome()
	helloWorld.Start()
	loop.LoopUnite()
	job.StartJob()
}
