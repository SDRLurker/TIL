#!/usr/bin/python
# -*- coding: cp949 -*-
import os
import ConfigParser
import time
import datetime

def pinghost(hostname):
	response = os.system("ping -c 1 " + hostname)

	#and then check the response...
	if response == 0:	# 살아있으면
		return True
	else:				# 죽었으면
		return False

def ReadConf():
	Config = ConfigParser.ConfigParser()
	path = os.getenv("HOME") + "/conf/ping.conf"
	Config.read(path)
	return Config

def printLog(logpath,str):
	f = open(logpath,'a')
	f.write(str+'\n')
	f.close

cfg = ReadConf()
poll = int(cfg.get("COMMON","POLL"))
hosts = cfg.get("COMMON","HOSTS").split(",")
threshold = int(cfg.get("COMMON","THRESHOLD"))	
now = datetime.datetime.now()
logpath = cfg.get("COMMON","LOGPATH") + "/ping." + now.strftime('%m%d')
host_dic = {}
for host in hosts:
	host_dic[host] = 0
while True:
	now = datetime.datetime.now()
	printLog(logpath, "[" + now.strftime('%Y-%m-%d %H:%M:%S') + "]")
	for host in hosts:
		result = pinghost(host)
		if result: 
			host_dic[host] = 0
		else:
			host_dic[host] = host_dic[host] + 1
		printLog(logpath, host + ":" + str(result))
		if host_dic[host] >= threshold:
			printLog(logpath, "[==ERROR==]" + host)
	time.sleep(poll)
