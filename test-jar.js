require('./out/goog/base.js');
var compileCljs = require('./out/cljs-compiler-jar.js').compile;

var cljsStr = ['(ns test.abc)',
            '(def foo 123)',
            '(map inc [1 2 3])',
            '(defmacro unless [pred a b] `(if (not ~pred) ~a ~b))',
            '#?(:cljs (println "cljs yo"))'].join('\n');

var result = compileCljs(cljsStr);

console.log(result);
