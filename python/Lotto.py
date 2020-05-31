#!/usr/bin/python
# -*- coding: utf-8 -*-

import random

SLIMIT = 1+2+3+4+5+6
ELIMIT = 45+44+43+42+41+40
class Lotto:
    def __init__(self, start=SLIMIT, end=ELIMIT):
        if start < SLIMIT or end < SLIMIT or start > ELIMIT or end > ELIMIT:
            raise ValueError("start 또는 end는 %d~%d여야 합니다.") % (SLIMIT, ELIMIT)
        self.nums = set()
        self.start = start
        self.end = end 
    def raffle(self):
        while sum(self.nums) < self.start or sum(self.nums) > self.end:
            self.nums = set()
            while len(self.nums) < 6:
                one = random.randrange(1,45+1)
                self.nums.add(one)
    def __str__(self):
        return "%s, sum=%s" % (sorted(self.nums), sum(self.nums))
    
if __name__ == "__main__":
    for i in range(5):
        lotto = Lotto(100,170)
        lotto.raffle()
        print(lotto)
