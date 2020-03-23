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

// Define C functions for the C++ class - as ctypes can only talk to C...
// ctypes는 C와만 대화할 수 있기 때문에 C++ 클래스를 위한 C 함수를 정의합니다.
extern "C"
{
    Foo* Foo_new(int n) {return new Foo(n);}
    void Foo_bar(Foo* foo) {foo->bar();}
    int Foo_foobar(Foo* foo, int n) {return foo->foobar(n);}
}
