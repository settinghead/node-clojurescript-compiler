# node-clojurescript-compiler

Npm package that compiles ClojureScript into JavaScript.

## Usage

```javascript
var compileCljs = require('clojurescript-compiler').compile;

var cljsStr = ['(ns test.abc)',
            '(def foo 123)',
            '(map inc [1 2 3])'].join('\n');

var result = compileCljs(cljsStr);

console.log(result);
```

Result:

```javascript
goog.provide('test.abc');
goog.require('cljs.core');

test.abc.foo = 123;

cljs.core.map.call(null,cljs.core.inc,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [1,2,3], null));

null
```

## Build

```basha
lein run -m clojure.main script/npm.clj
```
