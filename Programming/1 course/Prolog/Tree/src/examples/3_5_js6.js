"use strict";

chapter("Javascript 6+ features");

section("Method properties");

point = {
    x: 10,
    y: 20,
    getX() { return this.x; },
    setX(x) { this.x = x; },
    getY() { return this.y; },
    setY(y) { this.y = y; }
};
dumpObject("point", point);
example("point.getX()");
example("point.setX(100)");
example("point.getX()");

println();
println("Same as");
point = {
    x: 10,
    y: 20,
    getX: function() { return this.x; },
    setX: function(x) { this.x = x; },
    getY: function() { return this.y; },
    setY: function(y) { this.y = y; }
};

dumpObject("point", point);
example("point.getX()");
example("point.setX(100)");
example("point.getX()");

section("Classes");
class CPoint {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
    getX() {
        return this.x;
    }
    setX(x) {
        this.x = x;
    }
    getY() {
        return this.y;
    }
    setY(y) {
        this.y = y;
    }
    toString() {
        return `${this.constructor.name}(${this.x}, ${this.y})`;
    }
    static zero() {
        return new CPoint(0, 0);
    }
}

example("point = new CPoint(10, 20)");
dumpObject("point", point);
example("point.getX()");
example("point.setX(100)");
example("point.getX()");
example("CPoint.zero()");

println();
println("Same as");

function UPoint(x, y) {
    this.x = x;
    this.y = y;
}
UPoint.prototype.getX = function() { return this.x; },
UPoint.prototype.setX = function(x) { this.x = x; },
UPoint.prototype.getY = function() { return this.y; },
UPoint.prototype.setY = function(y) { this.y = y; }
UPoint.prototype.toString = function() { return `${this.constructor.name}(${this.x}, ${this.y})`; }

UPoint.zero = function () { return new UPoint(0, 0); }

example("point = new UPoint(10, 20)");
dumpObject("point", point);
example("point.getX()");
example("point.setX(100)");
example("point.getX()");
example("UPoint.zero()");

section("Inheritance");

class CShiftedPoint extends CPoint {
    constructor(x, y, dx, dy) {
        super(x, y);
        this.dx = dx;
        this.dy = dy;
    }
    getX() {
        return super.getX() + this.dx;
    }
    getY() {
        return super.getY() + this.dy;
    }
    toString() {
        return `${this.constructor.name}(${this.x}, ${this.y}, ${this.dx}, ${this.dy})`;
    }
}

example("point = new CShiftedPoint(10, 20, 1, 2)");
dumpObject("point", point);
example("point.getX()");
example("point.setX(100)");
example("point.getX()");

println();
println("Same as");

function UShiftedPoint(x, y, dx, dy) {
    UPoint.call(this, x, y);
        this.dx = dx;
        this.dy = dy;
}
UShiftedPoint.prototype = Object.create(UPoint.prototype);
UShiftedPoint.prototype.constructor = UShiftedPoint;
UShiftedPoint.prototype.getX = function() { return UPoint.prototype.getX.call(this) + this.dx; },
UShiftedPoint.prototype.getY = function() { return UPoint.prototype.getY.call(this) + this.dy; },
UPoint.prototype.toString = function() { return `${this.constructor.name}(${this.x}, ${this.y}, ${this.dx}, ${this.dy})`; }

example("point = new UShiftedPoint(10, 20, 1, 2)");
dumpObject("point", point);
example("point.getX()");
example("point.setX(100)");
example("point.getX()");


section("Sets");

const set = new Set().add(1).add(2).add(1);
example("set");
example("set.size");
example("set.has(1)");
example("set.has(3)");
example("for (const value of set.values()) { println(value); }");


section("Maps");

const mp = new Map().set("hello", 1).set("bye", 2).set("hello", 3);
example("mp");
example("mp.size");
example("mp.has('hello')");
example("mp.get('hello')");
example("mp.get('hello2')");
example("for (const entry of mp.entries()) { println(entry); }");
example("for (const key of mp.keys()) { println(key); }");
example("for (const value of mp.values()) { println(value); }");
example("mp.delete('hello')");
example("mp.get('hello')");
example("mp.has('hello')");

