#!/bin/bash
wget https://vault.centos.org/6.10/os/x86_64/Packages/ca-certificates-2018.2.22-65.1.el6.noarch.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/p11-kit-0.18.5-2.el6_5.2.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/p11-kit-trust-0.18.5-2.el6_5.2.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/libtasn1-2.3-6.el6_5.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-3.36.0-8.el6.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-sysinit-3.36.0-8.el6.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-tools-3.36.0-8.el6.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nspr-4.19.0-1.el6.x86_64.rpm  --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-softokn-3.14.3-23.3.el6_8.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-util-3.36.0-1.el6.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-softokn-freebl-3.14.3-23.3.el6_8.x86_64.rpm --no-check-certificate

rpm -Uvh ca-certificates-2018.2.22-65.1.el6.noarch.rpm p11-kit-0.18.5-2.el6_5.2.x86_64.rpm p11-kit-trust-0.18.5-2.el6_5.2.x86_64.rpm libtasn1-2.3-6.el6_5.x86_64.rpm nss-3.36.0-8.el6.x86_64.rpm nss-sysinit-3.36.0-8.el6.x86_64.rpm nss-tools-3.36.0-8.el6.x86_64.rpm nspr-4.19.0-1.el6.x86_64.rpm nss-softokn-3.14.3-23.3.el6_8.x86_64.rpm nss-util-3.36.0-1.el6.x86_64.rpm nss-softokn-freebl-3.14.3-23.3.el6_8.x86_64.rpm
