// pls kill me
"use strict"
/////////////////////////////////////////////////////////////////////////////////////////////
// COMMON FUNCTIONS
// new interface for operation
function newOperation(constructor, arg) {
    const temp = Object.create(constructor.prototype);
    constructor.apply(temp, arg);
    return temp;
}

//////////////////////////////////////////////////////////////////////////////////////////////
// NOT-STATIC COMPONENTS

// interface for Const and Variable
const ConstVarPrototype = {
    simplify: function () {
        return this;
    }
};

/////////////////////////////
// CONST
function Const(x) {
    this.getValue = x;
}

Const.prototype = Object.create(ConstVarPrototype);
Const.prototype.evaluate = function () {
    return this.getValue;
};
Const.prototype.toString = function () {
    return String(this.getValue);
}

const ZERO = new Const(0);
const ONE = new Const(1);

Const.prototype.diff = function () {
    return ZERO;
}

/////////////////////////////
// VARIABLE
const VARIABLES = {
    "x": 0,
    "y": 1,
    "z": 2
};

function Variable(name) {
    this.getName = name;
    this.NumberName = VARIABLES[name];
}

Variable.prototype = Object.create(ConstVarPrototype);
Variable.prototype.evaluate = function () {
    return arguments[this.NumberName];
};
Variable.prototype.toString = function () {
    return this.getName;
};
Variable.prototype.diff = function () {
    return (arguments[0] === this.getName ? ONE : ZERO);
}

/////////////////////////////////////////////////////////////////
// GRANDFATHER OPERATION
const OperationGrandfather = {
    evaluate:
        function (...arg) {
            let returned = this.Opera().map(function (value) {
                return value.evaluate.apply(value, arg);
            });
            return this.operate.apply(null, returned);
        },
    toString: function () {
        return this.Opera().join(" ") + " " + this.getSymbol();
    },
    diff: function (v) {
        let arg = this.Opera();
        arg = arg.concat(arg.map(function (x) {
            return x.diff(v);
        }));
        return this.doDiff.apply(this, arg);
    },

    simplify: function () {
        let arg = this.Opera().map(function (x) {
            return x.simplify();
        });
        let f;
        /*
        let f = arg.every(function (value) {
            return !(value instanceof Const);
        });*/
        for (let i = 0; i < arg.length; ++i) {
            if (!(arg[i] instanceof Const)) {
                f = true;
                break;
            }
        }
        let returned = newOperation(this.constructor, arg);
        if (!f) {
            return new Const(returned.evaluate());
        }
        if (this.doSimplify !== undefined) {
            return this.doSimplify.apply(this, arg);
        }
        return returned;
    }
};

/////////////////////////////////////////
// FATHER
function Operation(...operands) {
    this.Opera = function () {
        return operands;
    }
}

Operation.prototype = Object.create(OperationGrandfather);

function MakeOperation(make, symbol, operate, diff, simp) {
    this.constructor = make;
    this.operate = operate;
    this.doDiff = diff;
    this.doSimplify = simp;
    this.getSymbol = function () {
        return symbol;
    };
}

MakeOperation.prototype = Operation.prototype;

function operationInterface(symbol, operate, diff, simp) {
    let returned = function () {
        let arg = arguments;
        Operation.apply(this, arg);
    };
    returned.prototype = new MakeOperation(returned, symbol, operate, diff, simp);
    return returned;
}

/////////////////////////////////////////////////////////////////////
// OPERATIONS
function isZero() {
    return ((arguments[0] instanceof Const) && (arguments[0].getValue === 0));
}

function isF() {
    return ((arguments[0] instanceof Const) && (arguments[0].getValue === 1));
}

const Add = operationInterface(
    "+",
    function (a, b) {
        return a + b;
    },
    function (a, b, aa, bb) {
        return new Add(aa, bb);
    },
    function (a, b) {
        if (isZero(a)) return b;
        if (isZero(b)) return a;
        return new Add(a, b);
    }
);

const Subtract = operationInterface(
    "-",
    function (a, b) {
        return a - b;
    },
    function (a, b, aa, bb) {
        return new Subtract(aa, bb);
    },
    function (a, b) {
        if (isZero(b)) return a;
        return new Subtract(a, b);
    }
);

const Multiply = operationInterface(
    "*",
    function (a, b) {
        return a * b;
    },
    function (a, b, aa, bb) {
        return new Add(new Multiply(aa, b), new Multiply(a, bb));
    },
    function (a, b) {
        if (isZero(a) || isZero(b)) return ZERO;
        if (isF(b)) return a;
        if (isF(a)) return b;
        return new Multiply(a, b);
    }
);

const Divide = operationInterface(
    "/",
    function (a, b) {
        return a / b;
    },
    function (a, b, aa, bb) {
        return new Divide(new Subtract(new Multiply(aa, b), new Multiply(a, bb)), new Multiply(b, b));
    },
    function (a, b) {
        if (isZero(a)) return ZERO;
        if (isF(b)) return a;
        return new Divide(a, b);
    }
);
const Negate = operationInterface(
    "negate",
    function (a) {
        return -a;
    },
    function (a, aa) {
        return new Negate(aa);
    },
    function (a) {
        return a;
    }
);

const ArcTan = operationInterface(
    "atan",
    function (a) {
        return Math.atan(a);
    },
    function (a, aa) {
        return new Divide(aa, new Add(new Multiply(a, a), ONE));
    },
    function(a) {
        return a;
    }
);

const ArcTan2 = operationInterface(
    "atan2",
    function(a, b) {
        return Math.atan2(a, b);
    },
    function(a, b, aa, bb) {
        return new Divide(new Subtract(new Multiply(b, aa), new Multiply(a, bb)), new Add(new Multiply(a, a), new Multiply(b, b)));
    }
)


////////////////////////////////////////////////////////////////////
// Time to old program: Parser
const OP = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "negate": Negate,
    "atan": ArcTan,
    "atan2": ArcTan2
};
const COUNT_ARG = {
    "+": 2,
    "-": 2,
    "*": 2,
    "/": 2,
    "negate": 1,
    "atan": 1,
    "atan2": 2
};

let parse = function (expression) {
    const parted = expression.split(" ").filter(function (value) {
        return value.length > 0
    });
    let stack = [];
    for (let i = 0; i < parted.length; i++) {
        if (parted[i] in OP) {
            let arg = [];
            for (let j = 0; j < COUNT_ARG[parted[i]]; j++) {
                arg.push(stack.pop());
            }
            arg.reverse();
            stack.push(newOperation(OP[parted[i]], arg));
        } else {
            if (parted[i] in VARIABLES) {
                stack.push(new Variable(parted[i]));
            } else {
                stack.push(new Const(parseInt(parted[i])));
            }
        }
    };
    return stack.pop();
};
