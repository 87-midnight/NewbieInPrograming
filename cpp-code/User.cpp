//
// Created by PC on 2021/4/21.
//

#include "User.h"

char32_t User::getName() const {
    return name;
}

void User::setName(char32_t name) {
    User::name = name;
}

int User::getAge() const {
    return age;
}

void User::setAge(int age) {
    User::age = age;
}

int User::getGender() const {
    return gender;
}

void User::setGender(int gender) {
    User::gender = gender;
}
