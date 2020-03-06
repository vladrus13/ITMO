"use strict";

chapter("Functions");
section("dump");

const dump = function() {
    for (let i = 0; i < arguments.length; i++) {
        println(arguments[i]);
    }
};
println("dump =", dump);
dump(1, 2, "hello", null, undefined);

println();

const dump2 = function() {
    for (const arg of arguments) {
        println(arg);
    }
};
println("dump2 =", dump2);
dump(1, 2, "hello", null, undefined);


section("sum");

let sum = function(...args) {
    let result = 0;
    for (const arg of args) {
        result += arg;
    }
    return result;
};
println("sum =", sum);
example("sum(1, 2, 3)");


section("minimum");

let minimum = function() {
    let result = Infinity;
    for (const arg of arguments) {
        if (result > arg) {
            result = arg;
        }
    }
    return result;
};
println("minimum =", minimum);
example("minimum(1, -2, 3)");


section("Named functions and arguments");

function min(a, b) {
    return a < b ? a : b;
}
example("min");
example("min(1, -1)");
let m = min;
example("m(1, -1)");
example("m(1)");
example("m()");

section("Default arguments");

function def(a = -10, b = -20) {
	return [a, b];
}
example("def");
example("def(1, 2)");
example("def(1)");
example("def()");


section("Rest argument");

function minRest(first, ...rest) {
    let result = first;
    for (const a of rest) {
        result = min(result, a);
    }
    return result;
}
example("minRest");
example("minRest(1)");
example("minRest(1, -1)");
example("minRest(1, -1, 2, -2)");

section("Arrow functions");

const minArrow = (first, ...rest) => {
    let result = first;
    for (const a of rest) {
        result = Math.min(result, a);
    }
    return result;
};
example("minArrow");
example("minArrow(1)");
example("minArrow(1, -1)");
example("minArrow(1, -1, 2, -2)");
