# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

#   编译本地库时我们需要的最小的cmake版本
cmake_minimum_required(VERSION 3.4.1)

#生成的so库在和CMakeLists.txt同级目录下的libs文件夹下
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/libs/${CMAKE_ANDROID_API})

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
# 相当于Android.mk 需要注意的是，如果是多次使用add_library，则会生成多个so库。如果想将多个本地文件编译到一个so库中，只要最后一个参数添加多个C/C++文件的相对路径就可以
add_library( # Sets the name of the library.设置编译生成本地库的名字
             native-lib

             # Sets the library as a shared library.库的类型
             SHARED

             # Provides a relative path to your source file(s).编译文件的路径
        com.yk_cdemo_JniUtils.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
# 添加一些我们在编译我们的本地库的时候需要依赖的一些库，这里是用来打log的库
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
# 关联自己生成的库和一些第三方库或者系统库
target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )