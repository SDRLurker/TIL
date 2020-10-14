#!/usr/bin/python3
import sys

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage) %s (file)" % sys.argv[0])
        sys.exit(1)

    m = 0
    with open(sys.argv[1], errors='ignore') as f:
        for line in f.readlines():
            m = max(m, len(line))
    print("max_col=%d" % m)
