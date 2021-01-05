cmake_minimum_required(VERSION 3.16)
project(MathLogicHW)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -D_DEBUG")

add_executable(MathLogicHW main.cpp Base.cpp Base.h ExpressionParser.cpp ExpressionParser.h SolveStatus.cpp SolveE.cpp SolveE.h)