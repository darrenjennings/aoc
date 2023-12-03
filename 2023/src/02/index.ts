type GameConfiguration = {
    red: number
    green: number
    blue: number
}

function parseTurn(text: string): GameConfiguration[] {
    return text.split(':').slice(1)[0].split(';').map(s => s.trim()).map(getTurn);
}

function getTurn(game: string): GameConfiguration {
    const red = game.match(/(\d+) red/g);
    const blue = game.match(/(\d+) blue/g);
    const green = game.match(/(\d+) green/g);

    return {
        red: red ? red.slice(0).map(s => s.match(/(\d+) red/)[1]).reduce((acc, curr) => acc += parseInt(curr), 0) : 0,
        blue: blue ? blue.slice(0).map(s => s.match(/(\d+) blue/)[1]).reduce((acc, curr) => acc += parseInt(curr), 0) : 0,
        green: green ? green.slice(0).map(s => s.match(/(\d+) green/)[1]).reduce((acc, curr) => acc += parseInt(curr), 0) : 0
    }
}

export function isGamePossible(config: GameConfiguration, gameStr: string): number | false {
    const gameId = gameStr.match(/Game (\d+):/)[1]

    const turns = parseTurn(gameStr);
    for (let j = 0; j < turns.length; ++j) {
        const turn = turns[j]
        if (turn.blue > config.blue || turn.green > config.green || turn.red > config.red) {
            // One of the turns ended up being not possible!
            return false
        }
    }

    return parseInt(gameId);
}

function minGame(gameStr: string): GameConfiguration {
    const turns = parseTurn(gameStr);
    const max: GameConfiguration = {
        red: 0,
        green: 0,
        blue: 0
    }
    
    for (let j = 0; j < turns.length; ++j) {
        const turn = turns[j]
        if (turn.red > max.red) { max.red = turn.red }
        if (turn.green > max.green) { max.green = turn.green }
        if (turn.blue > max.blue) { max.blue = turn.blue }
    }

    return max;
}

/** Part One */
export function sumOfPossibleGameIds(config: GameConfiguration, text: string): number {
    return text.trim().split('\n').reduce((acc, curr) => {
        const gameIsPossible = isGamePossible(config, curr)
        acc += gameIsPossible ? gameIsPossible : 0
        return acc
    }, 0)
}

/** Part Two */
export function powerOfGames(text: string) :number {
    return text.trim().split('\n').reduce((acc, curr) => {
        const min = minGame(curr)
        return acc += min.blue * min.green * min.red
    }, 0)
}
