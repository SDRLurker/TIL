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
