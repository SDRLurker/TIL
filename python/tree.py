#! /usr/bin/env python

# tree.py
#
# Written by Doug Dahms
#
# Prints the tree structure for the path specified on the command line

from os import listdir, sep, getcwd
from os.path import abspath, basename, isdir
from sys import argv

def tree(dir, padding, print_files=False):
    print(padding[:-1] + '+-' + basename(abspath(dir)) + '/')
    padding = padding + ' '
    files = []
    if print_files:
        files = listdir(dir)
    else:
        files = [x for x in listdir(dir) if isdir(dir + sep + x)]
    count = 0
    for file in files:
        count += 1
        print(padding + '|')
        path = dir + sep + file
        if isdir(path):
            if count == len(files):
                tree(path, padding + ' ', print_files)
            else:
                tree(path, padding + '|', print_files)
        else:
            print(padding + '+-' + file)

def usage():
    return '''Usage: %s [-f] <PATH>
Print tree structure of path specified.
Options:
-f      Print files as well as directories
PATH    Path to process''' % basename(argv[0])

def print_if_dir(path, print_files=False):
    if isdir(path):
        tree(path, ' ')
    else:
        print('ERROR: \'' + path + '\' is not a directory')

def main():
    if len(argv) == 1:
        path = getcwd()
        print_if_dir(path)
    elif len(argv) == 2:
        # print just directories
        print_files = False
        if argv[1] == '-f':
            path = getcwd()
            print_files = True
        else:
            path = argv[1]
        print_if_dir(path, print_files)
    elif len(argv) == 3 and argv[1] == '-f':
        # print directories and files
        path = argv[2]
        print_if_dir(path, True)
    else:
        print(usage())

if __name__ == '__main__':
    main()
