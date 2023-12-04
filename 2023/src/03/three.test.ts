import { sumOfEngineParts, sumOfGearRatios } from './index'

describe('03', () => {
  describe('part 1', () => {
    it('sumOfEngineParts', () => {
      const schematic = `
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
`
      expect(sumOfEngineParts(schematic)).toBe(4361)
    })

    describe('part 2', () => {
      it('sumOfEngineParts', () => {
        const schematic = `
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
  `
        expect(sumOfGearRatios(schematic)).toBe(467835)
      })
    })
  })
})