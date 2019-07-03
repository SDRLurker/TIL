class Score:
    def __init__(self, s, b):
        self._s = s
        self._b = b
    def strikes(self):
        return self._s
    def balls(self):
        return self._b

class Game:
    def __init__(self, value):
        self.value = value
    def guess(self, gvalue):
        scnt = 0
        for vi in range(1, 3+1):
          if self._pos(self.value, vi) == self._pos(gvalue, vi):
            scnt += 1

        bcnt = 0
        for vi in range(1, 3+1):
          for gi in range(1, 3+1):
             if vi != gi and self._pos(self.value, vi) == self._pos(gvalue, gi):
                bcnt += 1

        return Score(scnt, bcnt)
    def _pos(self, v, p):
        CASE_DIC = {1: v % 10, 2: v // 10 % 10, 3: v // 100}
        if p not in CASE_DIC:
            raise ValueError()
        return CASE_DIC.get(p, 0)
