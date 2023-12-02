const numberMap = {
    'one': '1',
    'two': '2',
    'three': '3',
    'four': '4',
    'five': '5',
    'six': '6',
    'seven': '7',
    'eight': '8',
    'nine': '9',
    '1': 'one',
    '2': 'two',
    '3': 'three',
    '4': 'four',
    '5': 'five',
    '6': 'six',
    '7': 'seven',
    '8': 'eight',
    '9': 'nine'
}

export function calculateCalibrationSum(text: string): number {
    let res = []
    Object.values(numberMap).forEach(s => {
        const insertStr = Number.isInteger(parseInt(s)) ? s : numberMap[s]
        res[text.indexOf(s)] = insertStr
        res[text.lastIndexOf(s)] = insertStr
    });

    res = res.filter(r => r !== undefined)

    return parseInt(res[0] + res[res.length - 1]) || 0
}

export function calculateCalibrationTotals(text: string): number {
    return text
        .split('\n').map(line => calculateCalibrationSum(line))
        .reduce((acc, curr) => acc += curr, 0)
}
