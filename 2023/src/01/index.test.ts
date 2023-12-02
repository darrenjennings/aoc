import { calculateCalibrationSum, calculateCalibrationTotals } from './index'

describe('partners', () => {
  it('calculateCalibrationSum', () => {
    expect(calculateCalibrationSum('1abc2')).toEqual(12)
    expect(calculateCalibrationSum('pqr3stu8vwx')).toEqual(38)
    expect(calculateCalibrationSum('a1b2c3d4e5f')).toEqual(15)
    expect(calculateCalibrationSum('treb7uchet')).toEqual(77)
    expect(calculateCalibrationSum('treb7877uchet')).toEqual(77)
    expect(calculateCalibrationSum('two1nine')).toEqual(29)
    expect(calculateCalibrationSum('eightwothree')).toEqual(83)
    expect(calculateCalibrationSum('abcone2threexyz')).toEqual(13)
    expect(calculateCalibrationSum('xtwone3four')).toEqual(24)
    expect(calculateCalibrationSum('4nineeightseven2')).toEqual(42)
    expect(calculateCalibrationSum('zoneight234')).toEqual(14)
    expect(calculateCalibrationSum('7pqrstsixteen')).toEqual(76)
    expect(calculateCalibrationSum('9cbncbxclbvkmfzdnldc')).toEqual(99)
    expect(calculateCalibrationSum('9q')).toEqual(99)
    expect(calculateCalibrationSum('3nine39')).toEqual(39)
    expect(calculateCalibrationSum('eight82cppmnpvkvthreesevenseven8h')).toEqual(88)
  })

  it('calculateCalibrationTotals', () => {
    expect(calculateCalibrationTotals(`
      1abc2
      pqr3stu8vwx
      a1b2c3d4e5f
      treb7uchet
    `)).toEqual(142)

    expect(calculateCalibrationTotals(`
      two1nine
      eightwothree
      abcone2threexyz
      xtwone3four
      4nineeightseven2
      zoneight234
      7pqrstsixteen
    `)).toEqual(281)
  })
})