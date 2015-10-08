(require '[cljs.build.api :as b])

(b/build "src/npm"
  {:output-to "cljs-compiler.js"
   :target :nodejs
   :optimizations :simple
   :static-fns true
  ;  :cache-analysis true
   :pretty-print true
   :optimize-constants true
   :verbose true})

(System/exit 0)
