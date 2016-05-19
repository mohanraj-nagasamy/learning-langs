// Quiz 1
function funky(o) {
  o = null;
}

var x = [];
funky(x);

console.log(x);

// Quiz 2

function swap(a,b) {
  var temp = a;
  a = b;
  b = temp;
}

var x = 1, y = 2;
swap(x, y);
console.log(x);

// Problem 1

function identity(p) {
  return p;
}

var identity = function identity(p) {
  return p;
};

console.log(identity(3));
// console.log(identity(null));

// Problem 2
var add = function add(a, b) {
  return (a + b);
};

var mul = function mul(a, b) {
  return (a * b);
};

console.log(add(3,4))
console.log(mul(3,4))

// Problem 3

var identitfy = function(n) {
  return function() {
    return n;
  }
};

idf = identitfy(30);

console.log(idf());

// Problem 4

var addf = function(a) {
  return function(b) {
    return a + b;
  };
};

console.log((addf(3)))
console.log((addf(3)(4)))

// Problem 5

var applyf = function(f) {
 return function(a) {
   return function(b) {
     return f(a,b);
   };
 };
};

var addf = applyf(add);
var mulf = applyf(mul);

console.log((addf(3)(4)))
console.log((mulf(3)(4)))

// Problem 6

var curry = function(f, a) {
  return function(b) {
    return f(a, b);
  };
};

var curry2 = function(f, a) {
  return applyf(f)(a);
};

var add3 = curry(add, 3);

console.log(add3(4));
console.log((curry(mul, 5)(6)))
console.log((curry2(mul, 5)(6)))

// Problem 7

var inc1 = applyf(add)(1);
var inc2 = addf(1);
var inc3 = curry(add, 1);

console.log(inc1(5))
console.log(inc2(5))
console.log(inc3(inc3(5)))

// Problem 8

var methodize = function methodize(f) {
  return function(b) {
    return f(this, b);
  }
}

Number.prototype.add = methodize(add);

console.log("(3).add(4) : " + ((3).add(4)));

// Problem 9
var demethodize = function demethodize(f) {
  return function(a, b) {
    return f.call(a,b);
  };
};

console.log("demethodize : " + (demethodize(Number.prototype.add)(5,6)))

// Problem 10
var twice = function twice(f) {
  return function(num) {
    return f(num, num);
  };
};

var double = twice(add);

console.log("double : " + double(11));

var squre = twice(mul);

console.log("squre : " + squre(11));

// Problem 11
var composeu = function composeu(f1, f2) {
  return function(num) {
    return f2(f1(num));
  }
};

var doubleAndSqure = composeu(double, squre);

console.log("doubleAndSqure : " + doubleAndSqure(3));

// Problem 12
var composeb = function composeu(f1, f2) {
  return function(a,b,c) {
    return f2(f1(a,b), c);
  }
};

var addAndMul = composeb(add, mul);

console.log("addAndMul : " + addAndMul(2,3,5));

// Problem 13

// call only once

var once = function once(f) {
  var result = null;

  return function(a,b){
    if(!result) {
      result = f(a,b);
    } else {
       throw new Error("You should be called me only once!!!");
    }
    return result;
  };
};

var once = function once(func) {
  return function() {
    var f = func;
    func = null;
    return f.apply(this, arguments);
  };
};

var add_once = once(add);
var mul_once = once(mul);

console.log("1 add_once : " + add_once(3, 40));
console.log("1 mul_once : " + (mul_once(3, 40)));

console.log("2 add_once : " + (once(mul)(3, 4)));
console.log("2 mul_once : " + (once(mul)(3, 40)));

// Problem 14

var counterf = function counterf(num) {
  return {
    num: num,
    inc: function () {
      this.num = this.num + 1;
      return this.num;
    },
    dec: function () {
      this.num = this.num - 1;
      return this.num;
    },
    toString: function () {
      return "Num : " + this.num;
    }
  };
};

var counterf = function counterf(num) {
  return {
    inc: function () {
      num += 1;
      return num;
    },
    dec: function () {
      num -= 1;
      return num;
    },
    toString: function () {
      return "Num : " + num;
    }
  };
};

var counter = counterf(10);

console.log("counter : " + counter);
console.log("counter : " + counter.inc());
console.log("counter : " + counter.dec());

// Problem 15

var revocable = function revocable(func) {
  return {
    invoke: function() {
      return func.apply(this, arguments);
    },
    revoke: function() {
      func = null;
    }
  };
};

var temp = revocable(add);
console.log("temp.invoke(1,2) :: " + temp.invoke(1,2))
console.log("temp.invoke(2,2) :: " + temp.invoke(2,2))
temp.revoke();
console.log("temp.invoke(2,5) :: " + temp.invoke(2,5))


