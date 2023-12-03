import { isGamePossible, sumOfPossibleGameIds, powerOfGames } from './index'

describe('02', () => {
  describe('part 1', () => {
    it('isGamePossible', () => {
      const config = { red: 12, green: 13, blue: 14 }
      expect(isGamePossible(config, `Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green`)).toBe(1)
      expect(isGamePossible(config, `Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue`)).toBe(2)
      expect(isGamePossible(config, `Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red`)).toBeFalsy()
      expect(isGamePossible(config, `Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red`)).toBeFalsy()
      expect(isGamePossible(config, `Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green`)).toBe(5)
      expect(isGamePossible(config, `Game 4: 6 blue, 6 green; 2 blue, 5 green, 1 red; 9 blue, 1 red, 1 green; 1 red, 6 green, 8 blue; 4 green, 1 red, 1 blue`)).toBe(4)
    })

    it('sumOfPossibleGameIds', () => {
      const config = { red: 12, green: 13, blue: 14 }
      expect(sumOfPossibleGameIds(config, `
      Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
      Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
      Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
      Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
      Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
      `)).toEqual(8)
    })
  })

  describe('Part 2', () => {
    expect(powerOfGames(`
      Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
      Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
      Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
      Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
      Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    `)).toEqual(2286)
  })
})