(require '[cljs.build.api :as b])

(b/watch "src/browser"
  {:output-to "main.js"
  ; :target :nodejs
   :optimizations :none
   :static-fns true
   :pretty-print true
   :optimize-constants true
   :verbose true})

; (System/exit 0)
