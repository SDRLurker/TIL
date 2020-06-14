#!/usr/bin/python

import os
import sys
if len(sys.argv) == 3:
    for src in os.listdir(os.curdir):
        if os.path.isfile(src) and src.find(sys.argv[1]) >= 0:
            desc = src.replace(sys.argv[1], sys.argv[2])
            os.rename(src, desc)
else:
    print sys.argv[0] + " {src} {desc} "
    print "mv only files from src pattern to desc pattern"
