package entity

import (
	"encoding/json"
	"fmt"
)

type User struct {
	UserId   float64 `json:userId`
	Username string  `json:userName`
	Gender   string  `json:gender`
}

func (user *User) InsertUser() {
	json, errs := json.Marshal(user)
	if errs != nil {
		fmt.Println("json marshal error:", errs)
	}
	fmt.Println("json data :", string(json))
}
