FROM centos:centos6

RUN yum install -y gcc gcc-c++ wget xz libgcc glibc-devel glibc-headers

# Run some tests
RUN gcc --version && \
    g++ --version && \
    which gcc && \
    which g++
RUN mkdir ~/tests && \
    cd ~/tests && \
    echo '#include <iostream>' > main.cpp && \
    echo 'using namespace std;' >> main.cpp && \
    echo 'int main() {' >> main.cpp && \
    echo '    cout << "Hello world!" << endl;' >> main.cpp && \
    echo '    return 0;' >> main.cpp && \
    echo '}' >> main.cpp && \
    g++ main.cpp -o main && \
    ./main

# Download and compile GCC-5
# http://en.librehat.com/blog/build-gcc-5-dot-2-on-rhel-6/

# Download and extract source code
ENV gcc_version "5.3.0"
RUN wget --no-verbose \
        http://ftpmirror.gnu.org/gcc/gcc-${gcc_version}/gcc-${gcc_version}.tar.bz2 && \
    tar xf gcc-${gcc_version}.tar.bz2
RUN wget --no-verbose \
        https://gmplib.org/download/gmp/gmp-6.1.0.tar.xz && \
    tar xf gmp-6.1.0.tar.xz && \
    mv gmp-6.1.0 gcc-${gcc_version}/gmp
RUN wget --no-verbose \
        ftp://ftp.gnu.org/gnu/mpc/mpc-1.0.3.tar.gz && \
    tar xf mpc-1.0.3.tar.gz && \
    mv mpc-1.0.3 gcc-${gcc_version}/mpc
RUN wget --no-verbose \
        https://ftp.gnu.org/gnu/mpfr/mpfr-3.1.4.tar.xz && \
    tar xf mpfr-3.1.4.tar.xz && \
    mv mpfr-3.1.4 gcc-${gcc_version}/mpfr

# Compile and install GCC
# "we highly recommend that GCC be built into a separate directory from the sources which does not reside within the source tree"
RUN mkdir gcc-${gcc_version}_build && \
    cd gcc-${gcc_version}_build && \
    ../gcc-${gcc_version}/configure \
        --prefix=/usr \
        --disable-multilib \
        --enable-languages=c,c++ \
        --enable-libstdcxx-threads \
        --enable-libstdcxx-time \
        --enable-shared \
        --enable-__cxa_atexit \
        --disable-libunwind-exceptions \
        --disable-libada \
        --host x86_64-redhat-linux-gnu \
        --build x86_64-redhat-linux-gnu \
        --with-default-libstdcxx-abi=gcc4-compatible
RUN cd gcc-${gcc_version}_build && make -j4
RUN cd gcc-${gcc_version}_build && make install

# Validate the installed compiler
RUN hash -r && \
    gcc --version && \
    g++ --version && \
    which gcc && \
    which g++

# Register new libraries with `ldconfig`
RUN echo "/usr/local/lib64" > usrLocalLib64.conf && \
    mv usrLocalLib64.conf /etc/ld.so.conf.d/ && \
    ldconfig


# Clean out all the garbage
RUN rm -rf ~/${gcc_release} ~/{gcc_release}_build ~/tests
