(require '[cljs.build.api :as b])

(b/watch "src/node"
  {:output-to "out/cljs-compiler.js"
   :target :nodejs
   :optimizations :none
   :pretty-print true
   :verbose true})

; (System/exit 0)
