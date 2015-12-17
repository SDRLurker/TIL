#!/usr/bin/python
# -*- coding: cp949 -*-
import sys
import re

def split_by_n( seq, n ):
    """A generator to divide a sequence into chunks of n units."""
    while seq:
        yield seq[:n]
        seq = seq[n:]

if len(sys.argv) == 4:
    rfile = sys.argv[1]
    wfile = sys.argv[2]
    n = int(sys.argv[3])

    rfp = open(rfile, "r")
    data = rfp.read()
    result = list(split_by_n(data,n))
    wfp = open(wfile, "w")
    for line in result:
        wfp.write(line+'\n')
else:
    print 'slice.py [rfile] [wfile] [n]'
