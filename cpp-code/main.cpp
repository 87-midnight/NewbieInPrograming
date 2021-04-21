//
// Created by PC on 2021/4/21.
//
#include <iostream>
#include "User.h"
#include <nlohmann/json.hpp>
using nlohmann::json ;
using namespace std;

int main(){
    cout <<  "hello world" <<endl;
    User user{};
    user.setAge(12);
    user.setName('c');
    user.setGender(1);
    string str = "{\"name\":\"computer\"}";
    auto json_obj = json::parse(str);
    cout<< "json value:"<<json_obj<<endl;
    return 0;
}
