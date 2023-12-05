type CardNumbers = {
    winning: number[],
    yours: number[]
}

function cardNumbers(str: string[]): CardNumbers {
    const hand = str.map(numbers => numbers.trim().split('|'))[0].map(a => a.trim().split(" ").filter(i => i !== ''))
    return {
        winning: hand[0].map(i => parseInt(i)), yours: hand[1].map(i => parseInt(i))
    }
}

function aggregateGame(card: CardNumbers, operand: (p: number) => number): number {
    let points = 0;
    card.winning.forEach(i => {
        if (card.yours.includes(i)) {
            if (points == 0) {
                points = 1
            } else {
                points = operand(points)
            }
        }
    })

    return points
}

export function sumGame(card: CardNumbers): number {
    return aggregateGame(card, p => p*2)
}

export function getNumberWinningCards(card: CardNumbers): number {
    return aggregateGame(card, p => p + 1)
}

function getCards(text: string): CardNumbers[] {
    return text.split('\n').filter(a => a != '').map(line => {
        const cardNumbersStrs: string[] = line.split(":").slice(1)
        return cardNumbers(cardNumbersStrs)
    })
}

/** Part One */
export function totalPoints(input: string) {
    return getCards(input).reduce((acc, curr) => acc += sumGame(curr), 0);
}

/** Part Two */
export function totalScratchCards(input: string) {
    const cards = getCards(input);
    const copies = {}
    cards.forEach((c, index) => {
        const gameId: number = index + 1;
        const numberWinningCards = getNumberWinningCards(c)
        copies[gameId] ? copies[gameId].push(gameId) : (copies[gameId] = [gameId])

        const numCopies = copies[gameId].length;
        for (let j = 0; j < numCopies; ++j) {
            for (let i = gameId + 1; i < gameId + 1 + numberWinningCards; ++i) {
                copies[i] ? copies[i].push(i) : (copies[i] = [i])
            }
        }
    })
    return Object.values(copies).map((d: []) => d.length).reduce((acc, curr) => acc += curr, 0)
}
