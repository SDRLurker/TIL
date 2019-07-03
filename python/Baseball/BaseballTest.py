import unittest
from Baseball import Game, Score

class BaseballTest(unittest.TestCase):
  def test_nomatch(self):
    # Given: 정답이 479인 게임에서
    game = Game(479)

    # When: 123을 예측하면
    s = game.guess(123)

    # Then: 0 스트라이크, 0볼
    self.assert_no_match(s)
    
    self.assert_no_match(game.guess(568))
    self.assert_no_match(game.guess(321))

    self.assert_match(game.guess(359), 1, 0)
    self.assert_match(game.guess(372), 1, 0)
    self.assert_match(game.guess(486), 1, 0)

    self.assert_match(game.guess(478), 2, 0)
    self.assert_match(game.guess(429), 2, 0)
    self.assert_match(game.guess(379), 2, 0)
    self.assert_match(game.guess(489), 2, 0)

    self.assert_match(game.guess(124), 0, 1)
    self.assert_match(game.guess(142), 0, 1)
    self.assert_match(game.guess(712), 0, 1)
    self.assert_match(game.guess(127), 0, 1)
    self.assert_match(game.guess(912), 0, 1)
    self.assert_match(game.guess(192), 0, 1)

    self.assert_match(game.guess(497), 1, 2)
    self.assert_match(game.guess(974), 1, 2)
    self.assert_match(game.guess(749), 1, 2)

    self.assert_match(game.guess(794), 0, 3)
    self.assert_match(game.guess(947), 0, 3)

    self.assert_all_strikes(game.guess(479))
    game2 = Game(124)
    self.assert_all_strikes(game2.guess(124))

  def assert_match(self, score, s, b):
    self.assertEqual(score.strikes(), s)
    self.assertEqual(score.balls(), b)

  def assert_all_strikes(self, s):
    self.assertEqual(s.strikes(), 3)
    self.assertEqual(s.balls(), 0)

  def assert_no_match(self, s):
    self.assertEqual(s.strikes(), 0)
    self.assertEqual(s.balls(), 0)

if __name__ == '__main__':
    unittest.main(warnings='ignore')
