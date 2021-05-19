### 출처 및 응용

[http://www.auctoris.co.uk/2017/04/29/calling-c-classes-from-python-with-ctypes/](http://www.auctoris.co.uk/2017/04/29/calling-c-classes-from-python-with-ctypes/)

[https://sdr1982.tistory.com/263](https://sdr1982.tistory.com/263)

# JNA로 Java에서 C++ 클래스 호출하기

저는 Java에서 C++ 클래스를 호출하고 싶어서 최근에 스스로 방법을 찾았습니다. 저는 C++ 라이브러리를 직접 호출하고 싶었습니다.

저는 진행하기 전에 이를 Java로 하기 위한 다양한 방법이 있다는 것을 말하고 싶습니다. 그리고 저는 작동한 것 중 하나를 선택하였습니다. 다른 기술도 사용 가능하며 어떤 기술이 '최상'인지에 대한 의견은 상당히 분분해 보입니다.

C++ 클래스로 시작하기 위해 평범하게 작성하였습니다.

```c++
#include <iostream>

// A simple class with a constuctor and some methods...
// 생성자와 몇 개의 메소드를 가지는 간단한 클래스...

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

JNA에서는 C++을 사용할 수 없기 때문에 C++ 코드 주변에 C wrapper를 놓을 것입니다. 이를 하기 위해 파일 제일 밑에 다음 부분에 코드를 추가합니다.

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
우리는 우리 코드에서 libfoo.so 파일을 빌드해야 합니다.  
다음을 쉘에서 입력하세요.

```shell
$ g++ -c -fPIC foo.cpp -o foo.o
$ g++ -shared -Wl,-soname,libfoo.so -o libfoo.so foo.o 
```

gradle에서 JNA를 fat-jar로 컴파일하기 위해 빌드 스크립트를 만들었습니다.  
gradle 3.4.1에서 잘 작동함을 확인하였습니다.

```
apply plugin: 'java'
apply plugin: 'com.github.johnrengelman.shadow'

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.jengelman.gradle.plugins:shadow:2.0.1'
    }
}

repositories {
 mavenCentral()
}

dependencies {
  compile 'net.java.dev.jna:jna:4.1.0'
  compile 'net.java.dev.jna:jna-platform:4.1.0'
}

jar {
   finalizedBy shadowJar
   manifest {
      attributes 'Main-Class': 'hello.Foo',
                 'Implementation-Title': 'Foo jna Project',
                 'Implementation-Version': '1.0'
   }
}
```

자바 소스는 src/main/java/hello/Foo.java 로 작성하였습니다.

```
jna_demo$ vi src/main/java/hello/Foo.java
package hello;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public class Foo {
    public interface FooLib extends Library {
        Pointer Foo_new(int n);
        void Foo_bar(Pointer foo);
        int Foo_foobar(Pointer foo, int n);
    }

    private String sopath;
    private FooLib INSTANCE;
    private Pointer self;

    private void loadLibrary(int n) {
        INSTANCE = (FooLib)Native.loadLibrary(
            sopath, FooLib.class
        );
        self = INSTANCE.Foo_new(n);
    }

    public Foo(int n) {
        sopath = "libfoo.so";
        loadLibrary(n);
    }
    public Foo(String sopath, int n) {
        this.sopath = sopath;
        loadLibrary(n);
    }

    public void bar() {
        INSTANCE.Foo_bar(self);
    }

    public int foobar(int n) {
        return INSTANCE.Foo_foobar(self, n);
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "/libfoo.so";
        System.out.println(path);
        Foo f = new Foo(path,5);

        f.bar();

        System.out.println("f.foobar(7) = " + f.foobar(7));

        int x = f.foobar(2);
        System.out.println("x = " + x);
    }
}
```

gradle로 Java 소스를 build 합니다.

```shell
jna_demo$ gradle build
```

빌드를 하면 build/libs 경로에 2개 파일이 생성됩니다.

* content.jar: 원래 내 프로젝트
* content-all.jar: JNA 라이브러리가 포함된 Fat-JAR 프로젝트

다음처럼 Makefile을 생성합니다.

```
SRCS    = foo.cc
OBJS    = foo.o

CFLAGS = $(CFLAG) -D_REENTRANT -D_THREAD_SAFE -D$(_OSTYPE_)
CPPFLAGS= $(CPPFLAG) -D_REENTRANT -D_THREAD_SAFE -D$(_OSTYPE_)

all : libfoo.so

libfoo.so :
	g++ -fPIC -c $(SRCS)
	g++ -shared -Wl,-soname,$@ -o $@ $(OBJS)
	gradle build

clean:
	rm -f *.o core *.out .*list *.ln *.so
	gradle clean
```

다음처럼 C++, Java 소스를 make로 한꺼번에 build 할 수 있습니다.

```shell
jna_demo$ make clean && make
```

다음은 실행 결과입니다.

```shell
jna_demo$ java -jar build/libs/jna_demo-all.jar
/home/sdrlurker/jna_demo/libfoo.so
Value is 5
f.foobar(7) = 12
x = 7
```
