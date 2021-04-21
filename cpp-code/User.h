//
// Created by PC on 2021/4/21.
//

#ifndef CPP_CODE_USER_H
#define CPP_CODE_USER_H


class User {
private:
    char32_t name;
    int age;
    int gender;
public:
    char32_t getName() const;

    void setName(char32_t name);

    int getAge() const;

    void setAge(int age);

    int getGender() const;

    void setGender(int gender);
};


#endif //CPP_CODE_USER_H
