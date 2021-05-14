## 출처

http://www.auctoris.co.uk/2017/04/29/calling-c-classes-from-python-with-ctypes/

---

# ctypes로 Python에서 C++ 클래스 호출하기

저는 파이썬에서 C++ 클래스를 호출하고 싶어서 최근에 스스로 방법을 찾았습니다. 저는 (Thrift를 사용하여 전에 했던 것처럼 - [Python과 C++을 위한 Apache Thrift 사용하기](http://www.auctoris.co.uk/2016/08/17/using-apache-thrift-for-python-c/)를 보세요.) 분리된 프로세스를 호출하고 싶지 않았고 C++ 라이브러리를 직접 호출하고 싶었습니다.

저는 진행하기 전에 이를 파이썬으로 하기 위한 다양한 방법이 있다는 것을 말하고 싶습니다. 그리고 저는 작동한 것 중 하나를 선택하였습니다. 다른 기술도 사용 가능하며 어떤 기술이 '최상'인지에 대한 의견은 상당히 분분해 보입니다.

C++ 클래스로 시작하기 위해 평범하게 작성하였습니다.

```c++
#include <iostream>

// A simple class with a constuctor and some methods...

class Foo
{
    public:
        Foo(int);
        void bar();
        int foobar(int);
    private:
        int val;
};

Foo::Foo(int n)
{
    val = n;
}

void Foo::bar()
{
    std::cout << "Value is " << val << std::endl;
}

int Foo::foobar(int n)
{
    return val + n;
}
```


다음 ctypes 시스템은 C++을 사용할 수 없기 때문에 C++ 코드 주변에 C wrapper를 놓을 것입니다. 이를 하기 위해 파일 제일 밑에 다음 부분에 코드를 추가합니다.

```c++
// ctypes는 C와만 대화할 수 있기 때문에 C++ 클래스를 위한 C 함수를 정의합니다.

extern "C"
{
    Foo* Foo_new(int n) {return new Foo(n);}
    void Foo_bar(Foo* foo) {foo->bar();}
    int Foo_foobar(Foo* foo, int n) {return foo->foobar(n);}
}
```

호출하기 원하는 각 메소드를 클래스 기반이 아닌 이름으로 제공해야 함을 알아두세요.

우리는 우리 코드에서 lib.so 파일을 빌드해야 합니다.

다음을 쉘에서 입력하세요.

```shell
$ g++ -c -fPIC foo.cpp -o foo.o
$ g++ -shared -Wl,-soname,libfoo.so -o libfoo.so foo.o 
```


또는 CMake를 사용할 수 있습니다.

다음은 foo.cpp를 빌드하기 위한 CMakeLists.txt 입니다.

```
cmake_minimum_required(VERSION 2.8.9)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -Wall")
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${CMAKE_SOURCE_DIR}")
set(CMAKE_MACOSX_RPATH 1)

project (foo)
set (SOURCE foo.cpp)
add_library(foo MODULE ${SOURCE}) 
```

저는 Mac에서 빌드를 해서 MacOS를 위해 4번째 줄을 추가하였습니다. 리눅스에서도 잘 작동하겠지만 필요는 없습니다.

이제 C++로 컴파일 된 내용을 작성합니다. 우리는 클래스에 대한 Python wrapper를 빌드하려 합니다.

```python
import ctypes

lib = ctypes.cdll.LoadLibrary('./libfoo.so')

class Foo(object):
    def __init__(self, val):
        lib.Foo_new.argtypes = [ctypes.c_int]
        lib.Foo_new.restype = ctypes.c_void_p

        lib.Foo_bar.argtypes = [ctypes.c_void_p]
        lib.Foo_bar.restype = ctypes.c_void_p

        lib.Foo_foobar.argtypes = [ctypes.c_void_p, ctypes.c_int]
        lib.Foo_foobar.restype = ctypes.c_int

        self.obj = lib.Foo_new(val)

    def bar(self):
        lib.Foo_bar(self.obj)
   
    def foobar(self, val):
        return lib.Foo_foobar(self.obj, val)
```

리턴 값의 타입과 argument 타입을 정의하는 요구사항을 적으세요. (하나도 리턴하지 않으면 예시로 void를 리턴합니다.) 이것이 없으면 segmentation fault(등)를 얻을 것입니다.
이제 모든 것을 다 하였고 모듈을 빌드해야 합니다. 파이썬에서 간단히 그것을 import할 수 있습니다.
예를 들어

```python
from foo import Foo

# 우리는 5라는 값으로 Foo 객체를 생성할 것입니다...
f=Foo(5)

# f.bar()를 호출하는 것은 값을 포함한 메시지를 출력할 것입니다...
f.bar()

# 이제 f, Foo 객체에서 저장되어 있는 값에 값(7)을 더하기 위해 foobar를 사용합니다.
print (f.foobar(7))

# 또 한 번 같은 메소드를 호출합니다 - 이 번엔 일반적인 파이썬 정수를 
# 보여줄 것입니다...

x = f.foobar(2)
print (type(x))
```

이 간단한 데모를 위한 전체 소스 코드는 여기에 있습니다.

https://github.com/Auctoris/ctypes_demo
