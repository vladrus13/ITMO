"use strict";
const operation = function (op) {
    return function (...operations) {
        return function () {
            let returned = [];
/*
            operations.map(function (value, i) {
                // console.log(value);
                // console.log(value(...arguments));
                returned[i] = value(...arguments);
            });
*/
            for (let i = 0; i < operations.length; i++) {
                // console.log(operations[i]);
                // console.log(operations[i](...arguments));
                returned[i] = operations[i](...arguments);
            }

            return op.apply(null, returned);
        }
    }
};

const VARIABLES = {
    "x": 0,
    "y": 1,
    "z": 2
};

const variable = function (name) {
    return function () {
        return arguments[VARIABLES[name]];
    }
};

for (let i in VARIABLES) {
    this[i] = variable(i);
}

const cnst = function (value) {
    return function () {
        return value;
    }
};

const add = operation(function (a, b) {
    return a + b;
});

const subtract = operation(function (a, b) {
    return a - b;
});

const multiply = operation(function (a, b) {
    return a * b;
});

const divide = operation(function (a, b) {
    return a / b;
});

const negate = operation(function (a) {
    return -a;
});

const med3 = operation(function () {
    let arr = [arguments[0], arguments[1], arguments[2]];
    arr.sort(function (a, b) {
        return Number(a) - Number(b)
    });
    return arr[1];
});

const avg5 = operation(function () {
    // return (arguments) => arguments.reduce((a, b) => a + b) / arguments.length;
    return (arguments[0] + arguments[1] + arguments[2] + arguments[3] + arguments[4]) / 5;
});

const abs = operation(function (a) {
    return Math.abs(a);
});

const iff = operation(function () {
    if (arguments[0] >= 0) {
        return arguments[1];
    } else {
        return arguments[2];
    }
})

const CONSTS = {
    "pi": Math.PI,
    "e": Math.E,
    "one": 1,
    "two": 2
};

for (let ii in CONSTS) {
    this[ii] = cnst(CONSTS[ii]);
}

const parse = function (expression) {
    const OP = {
        "+": add,
        "-": subtract,
        "*": multiply,
        "/": divide,
        "negate": negate,
        "med3": med3,
        "avg5": avg5,
        "abs": abs,
        "iff": iff
    };
    const COUNT_ARG = {
        "+": 2,
        "-": 2,
        "*": 2,
        "/": 2,
        "negate": 1,
        "med3": 3,
        "avg5": 5,
        "abs": 1,
        "iff": 3
    };
    const parted = expression.split(" ").filter(function (value) {
        return value.length > 0
    });
    let stack = [];
    for (let i = 0; i < parted.length; i++) {
        if (parted[i] in CONSTS) {
            stack.push(cnst(CONSTS[parted[i]]));
        } else {
            if (parted[i] in OP) {
                let arg = [];
                for (let j = 0; j < COUNT_ARG[parted[i]]; j++) {
                    arg.push(stack.pop());
                }
                arg.reverse();
                stack.push(OP[parted[i]].apply(null, arg));
            } else {
                if (parted[i] in VARIABLES) {
                    stack.push(variable(parted[i]));
                } else {
                    stack.push(cnst(Number(parted[i])));
                }
            }
        }
    }
    return stack.pop();
};