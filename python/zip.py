#!/usr/bin/python
import os
import datetime
import sys

def zip_and_rm(dir, thres = 1024*1024*1024*2, ago=1):
    os.chdir(dir)
    list_dirs = os.walk(dir)
    yesterday = datetime.datetime.now().replace(hour=0, minute=0, second=0, microsecond=0) - datetime.timedelta(microseconds=1)
    for root, dirs, files in list_dirs:
        if root == dir:
            for f in files:
                if f.endswith('.tar.gz'):
                    continue
                path = os.path.join(root, f)
                stat = os.stat(path)
                file_stamp = datetime.datetime.fromtimestamp(stat.st_mtime)
                if stat.st_size >= thres and file_stamp <= yesterday:
                    print(yesterday, path, file_stamp, stat.st_size)
                    cmd = "tar cvfz %s.tar.gz %s" % (f, f)
                    os.system(cmd)
                    cmd = "\\rm %s" % f
                    os.system(cmd)

if __name__ == "__main__":
    if len(sys.argv) != 2 and len(sys.argv) != 3:
        print "Usage) %s [dir] (thres(GB))" % sys.argv[0]
        sys.exit(1)
    dir = sys.argv[1]
    thres = 1024*1024*1024*2
    if len(sys.argv) == 3:
        try:
            thres = 1024*1024*1024*int(sys.argv[2])
        except:
            thres = 1024*1024*1024*2
    print("dir=%s, thres = %d" % (dir, thres))
    zip_and_rm(dir, thres)
