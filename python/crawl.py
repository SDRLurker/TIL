#!/usr/bin/python
import requests
from bs4 import BeautifulSoup
import multiprocessing

HOST = "http://sdr1982.tistory.com"
def get_tistory_title(id):
    try:
        url = "%s/%d" % (HOST, id)
        headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}        
        r = requests.get(url, headers=headers)
        soup = BeautifulSoup(r.text, 'html.parser')
        title = soup.findAll("div", {"class":"titleWrap"})                
        return title[0].find("a").text
    except:
        return ""

if __name__ == "__main__":
    pool = multiprocessing.Pool(processes=2)
    s = 20
    e = 30
    res = pool.map(get_tistory_title, range(s,e+1))
    res_dic = {s+i:title for i, title in enumerate(res) if title}
    print(res_dic)
