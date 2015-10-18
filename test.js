var compileCljs = require('./cljs-compiler.js').compile;

var cljsStr = ['(ns test.abc)',
            '(def foo 123)',
            '(map inc [1 2 3])',
            '(defmacro unless [pred a b] `(if (not ~pred) ~a ~b))'].join('\n');

var result = compileCljs(cljsStr);

console.log(result);