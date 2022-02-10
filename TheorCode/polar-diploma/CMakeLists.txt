cmake_minimum_required(VERSION 3.20)
cmake_policy(SET CMP0054 NEW)
project(info_coding)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set (EXECUTABLE_OUTPUT_PATH bin/)

add_executable(main main.cpp polar/PolarCoder.cpp polar/PolarCoder.h)
add_executable(testing testing.cpp polar/PolarCoder.cpp polar/PolarCoder.h)
add_executable(signal signal_test.cpp polar/PolarCoder.cpp polar/PolarCoder.h)

set(CMAKE_THREAD_PREFER_PTHREAD ON)
set(THREADS_PREFER_PTHREAD_FLAG ON)
find_package(Threads REQUIRED)

set(AFF3CT_DIR "lib/aff3ct/build/lib/cmake/aff3ct-2.3.3")
find_package(AFF3CT CONFIG 2.3.3 REQUIRED)
target_link_libraries(signal PRIVATE aff3ct::aff3ct-static-lib)