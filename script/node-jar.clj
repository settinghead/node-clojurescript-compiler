(require '[cljs.build.api :as b])

(b/watch "src/node"
  {:output-to "out/cljs-compiler-jar.js"
   :target :nodejs
   :optimizations :simple
   :pretty-print true
   :verbose true})

; (System/exit 0)
