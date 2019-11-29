# -*- coding: cp949 -*-
import time
import subprocess
from collections import namedtuple
from datetime import datetime
import socket

# https://python-forum.io/Thread-how-can-I-find-the-disk-space-on-a-remote-hosts
def remote_df(user, ip, path):
    """
    Executes df on remote host and return
    (total, free, used) as int in bytes
    """
    Result = namedtuple('diskfree', 'total used free')

    myip = socket.gethostbyname(socket.gethostname())
    if ip == 'localhost' or ip == myip:
        output = subprocess.check_output(['df'], shell=False)
    else:
        output = subprocess.check_output(['ssh', '%s@%s' % (user, ip), '-C', 'df'], shell=False)
    #output = output.splitlines()
    output = output.split('\n')

    for line in output[1:]:
        #result = line.decode().split()
        result = line.split()
        if result[-1] == path:
            used = int(result[2])
            free = int(result[3])
            total = used + free
            #print(result)
            return Result(total, used, free)
    raise Exception('Path "%s" not found' % path)

def get_use_percent(user, ip, path):
    result = remote_df(user, ip,  path)
    return round(float(result[1]) / float(result[0]) * 100.0)



if __name__ == "__main__":
    THRESHOLD = 90.0
    SERVERS = () # 디스크 양을 검사하길 원하는 서버 IP(HOST)를 SERVERS 튜플에 넣으세요!
    while True:
        for server in SERVERS:
            percent = get_use_percent('', server, '/') # 계졍명을 첫번째 파라미터로 사용하세요.
            print(server,percent)
            if percent > THRESHOLD:
                msg = "[%s]서버 %s 디스크 사용량 %.2f%% 입니다. 파일을 삭제하세요!!" % (datetime.now().strftime('%Y/%m/%d %H:%M:%S'), server, percent)
                print(msg)
        time.sleep(60)
