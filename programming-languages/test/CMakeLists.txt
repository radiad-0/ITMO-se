cmake_minimum_required(VERSION 3.23)
project(test C)

set(CMAKE_C_STANDARD 99)

add_executable(test main.c test.h)

file(GLOB_RECURSE sources CONFIGURE_DEPENDS
*.c
*.h
*.h
)