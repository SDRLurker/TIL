import bitarray
def get_rectangles_area(rectangles): 
    b = [bitarray.bitarray('0'*1920) for _ in range(1080)]
    for x1,y1,x2,y2 in rectangles:
        for y in range(y1,y2):
            for x in range(x1,x2):
                b[y][x] = 1
    return sum(sum(l) for l in b)

import unittest
class test_solution(unittest.TestCase):
    def test_all(self):
        in_txt = """1 0 4 2
8 3 9 4
2 3 5 7
4 6 7 8
3 1 6 5
1 8 4 10
7 2 9 5
8 8 10 9
1 4 2 6"""
        in_rectangles = [ [int(s) for s in line.split()] for line in in_txt.split("\n")]
        self.assertEqual(get_rectangles_area(in_rectangles), 46)

if __name__ == "__main__":
    unittest.main()
