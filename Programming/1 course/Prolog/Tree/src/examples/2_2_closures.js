"use strict";

chapter("Closures");
section("Functions with state");

function incrementor(step) {
    let n = 0;
    return function() {
        n += step;
        return n;
    }
}

let inc = incrementor(10);
println("n and step are captured in closure");
println("    inc() = " + inc());
println("    inc() = " + inc());
println("    inc() = " + inc());


section("Be careful");

println("addersVar shares same var i:");
function addersVar(n) {
    let a = [];
    for (var i = 0; i < n; i++) {
        a.push(function(v) { return i + v; });
    }
    println("i = " + i);
    return a;
}

a = addersVar(3);
for (let j = 0; j < a.length; j++) {
    println("    addersVar[" + j + "] adds " + a[j](0));
}


section("Intermediate function trick");

println("addersFun has a copy of var i named w:");
function addersFun(n) {
    let a = [];
    for (let i = 0; i < n; i++) {
        a.push(
            (function(w) {
                return function(v) { return w + v; }
            })(i) // Call of declared intermediate function
        );
    }
    return a;
}

const adder = addersFun(3);
for (let j = 0; j < a.length; j++) {
    println("    addersFun[" + j + "] adds " + adder[j](0));
}
println();


section("Use let");

println("No issues with let i:");
function addersLet(n) {
    let a = [];
    for (let i = 0; i < n; i++) {
        a.push(function(v) { return i + v; });
    }
    return a;
}

a = addersLet(3);
for (let j = 0; j < a.length; j++) {
    println("    addersLet[" + j + "] adds " + a[j](0));
}


section("Common shared state");
function PrivatePoint(x, y) {
    println("Constructor called");
    let z = 0;
    this.getX = function() { return x; };
    this.setX = function(value) { x = value; };
    this.getY = function() { return y; };
    this.setY = function(value) { y = value; };
    this.getZ = function() { return z; };
    this.setZ = function(value) { z = value; };
}

let privatePoint = new PrivatePoint(10, 20);
dumpObject("privatePoint", privatePoint);

privatePoint.setX(100);
privatePoint.setZ(1000);
dumpObject("Modified privatePoint", privatePoint);
