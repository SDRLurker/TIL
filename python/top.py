#!/usr/bin/python

import subprocess
from fcntl import fcntl, F_GETFL, F_SETFL
from os import O_NONBLOCK, read
import re
from time import sleep

save = []

p = subprocess.Popen(['top','-b','-d 1'], stdout = subprocess.PIPE, stderr = subprocess.PIPE)
flags = fcntl(p.stdout, F_GETFL) # get current p.stdout flags
fcntl(p.stdout, F_SETFL, flags | O_NONBLOCK)
start=0
print 'hms','us','p1_cmd','p1_cpu','p1_mem','p2_cmd','p2_cpu','p2_mem','p3_cmd','p3_cpu','p3_mem'
while True:
    try:
        line = p.stdout.readline()
        if line.find('top') == 0:
            hms_str = re.search('[0-9][0-9]:[0-9][0-9]:[0-9][0-9]', line)
            hms = hms_str.group(0)
        if line.find('Cpu') == 0:
            us_str = re.search('[ 1]{0,1}[0-9]{1,2}.[0-9]%us', line)
            us = us_str.group(0).replace('%us','')
        if line.find('PID') > 0:
            start = 0
            proc_array = []
        if start >= 1 and start <= 3 and line.find('Tasks') < 0 and line.find('Cpu') < 0 and line.find('Mem') < 0:
            fields = line.split()
            proc_info = {'rank' : start, 'cpu' : fields[8], 'mem' : fields[9], 'exec':fields[11]}
            #print proc_info
            proc_array.append(proc_info)
            if start == 3:
                p1 = proc_array[0]
                p2 = proc_array[1]
                p3 = proc_array[2]
                print hms, us, p1['exec'], p1['cpu'], p1['mem'], p2['exec'], p2['cpu'], p2['mem'], p3['exec'], p3['cpu'], p3['mem']
        start = start + 1
    except IOError:
        pass
    sleep(0.0015)
