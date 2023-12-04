const gearStr = '*'
const emptyCell = '.'

function getAdjacent(matrix, i: number, j: number): number[] {
    return [matrix[i]?.[j + 1], matrix[i]?.[j - 1], matrix[i + 1]?.[j], matrix[i - 1]?.[j], matrix[i + 1]?.[j + 1], matrix[i + 1]?.[j - 1], matrix[i - 1]?.[j + 1], matrix[i - 1]?.[j - 1]]
                    .filter(c => c !== emptyCell).map(c => parseInt(c)).filter(Number);
}

function buildMatrixFrom(schematic: string): string[] {
    const lines = schematic.split('\n').filter(i => i !== '')
    const matrix = []
    for (let i = 0; i < lines.length; ++i) {
        matrix[i] = []
        for (let j = 0; j < lines[i].length; ++j) {
            matrix[i][j] = lines[i][j]
        }
    }


    for (let i = 0; i < lines.length; ++i) {
        let arr
        const digitRegex = /(\d+)/ig
        while ((arr = digitRegex.exec(lines[i])) !== null) {
            for (let j = arr.index; j < arr.index + arr[0].length; ++j) {
                matrix[i][j] = arr[0]
            }
        }
    }

    return matrix
}

/** Part One */
export function sumOfEngineParts(schematic: string) {
    const found = new Set()
    const matrix = buildMatrixFrom(schematic)

    for (let i = 0; i < matrix.length; ++i) {
        for (let j = 0; j < matrix[0].length; ++j) {
            const cell = matrix[i][j]
            if (cell !== emptyCell && !Number.isInteger(parseInt(cell))) {
                const adjacent = getAdjacent(matrix, i, j)
                adjacent.forEach(adj => found.add(`${i}-${j}-${adj}`))
            }
        }
    }


    return [...Array.from(found)].reduce((acc: number, curr: string) => {
        acc = acc + parseInt(curr.split('-')[2])

        return acc
    }, 0)
}

/** Part Two */
export function sumOfGearRatios(schematic: string) {
    const found = []
    const matrix = buildMatrixFrom(schematic)

    for (let i = 0; i < matrix.length; ++i) {
        for (let j = 0; j < matrix[0].length; ++j) {
            const cell = matrix[i][j]
            if (cell === gearStr) {
                const adjacent = getAdjacent(matrix, i, j)
                
                const adjacentSet = new Set<string>();
                adjacent.forEach(adj => {
                    adjacentSet.add(`${i}-${j}-${adj}`)
                });

                const adjacentSetArr = [...Array.from(adjacentSet)];
                if (adjacentSetArr.length == 2) {
                    found.push(parseInt(adjacentSetArr[0].split('-')[2]) * parseInt(adjacentSetArr[1].split('-')[2]))
                }
            }
        }
    }

    return found.reduce((acc: number, curr: number) => acc += curr, 0);
}
