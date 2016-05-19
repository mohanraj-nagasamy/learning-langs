//Using Global valriable

var names = ['zero'];
var  digit_name = function(n) {
  return  names[n];
}
console.log(digit_name(0));

// Inside function. But Slowwww
var  digit_name = function(n) {
  var names = ['zero', 'one'];
  return  names[n];
}
var names = ['zero'];
console.log(digit_name(1));

//Closure
var  digit_name = (function() {
  var names = ['zero', 'one', 'two'];

  return  function(n) {
    return names[n];
  };
}());
var names = ['zero'];
console.log(digit_name(2));

//Immediate function returns a function
var names = ['zero', 'one', 'two', 'three'];
var  digit_name = (function () {
  return function(n) {
    return names[n];
  };
}());
// var names = ['zero'];
console.log(digit_name(3));

//Lazy (don't do this)
var  digit_name = function(n) {
  var names = ['zero', 'one', 'two', 'three', 'four'];

  digit_name = function(n) {
    return names[n];
  };

  return digit_name(n)
};

var names = ['zero'];
console.log(digit_name(4));

// Closure conditional
var  digit_name = (function() {
  var names;
  return function(n) {
    if(!names) {
      names = ['zero', 'one', 'two', 'three', 'four', 'five'];
    }
    return names[n];
  };
}());

var names = ['zero'];
console.log(digit_name(5));

// Prototype

function Gizmo(id) {
  this.id = id;
}

Gizmo.prototype.toString = function () {
  return "gizmo " + this.id
};

var gizmo = new Gizmo("some-id");

console.log(gizmo)

function Hoozit(id) {
  this.id = id;
}


Hoozit.prototype = new Gizmo();
Hoozit.prototype.test = function (id) {
  return this.id === id
}


// Functional Inheritance
function gizmo(id) {
  return {
    id: id,
    toString: function () {
      return "gizmo " + this.id
    }
  };
}

function hoozit(id) {
 var that = gizmo(id);
 that.test = function (testid) {
   return testid === this.id
 }

 return that;
}

 // Without exposing id
function gizmo(id) {
  return {
    toString: function () {
      return "gizmo " + this.id
    }
  };
}

function hoozit(id) {
 var that = gizmo(id);
 that.test = function (testid) {
   return testid === id
 }

 return that;
}

//Temp

var add = function (a,b) {
  return a + b
}

add(2,2)
