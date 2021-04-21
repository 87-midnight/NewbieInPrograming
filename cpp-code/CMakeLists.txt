cmake_minimum_required(VERSION 3.17)
project(cpp_code)
include(FetchContent)

FetchContent_Declare(json
        GIT_REPOSITORY https://github.com/ArthurSonzogni/nlohmann_json_cmake_fetchcontent
        GIT_TAG v3.7.3)
FetchContent_MakeAvailable(json)


set(CMAKE_CXX_STANDARD 14)

add_executable(cpp_code
        main.cpp User.cpp User.h)

target_link_libraries(cpp_code PRIVATE nlohmann_json::nlohmann_json)
