(ns cljs-bootstrap.core
  (:require-macros [cljs.core]
                   [cljs.env.macros :refer [ensure with-compiler-env]]
                   [cljs.analyzer.macros :refer [no-warn]])
  (:require [cljs.pprint :refer [pprint]]
            [cljs.tagged-literals :as tags]
            [cljs.tools.reader :as r]
            [cljs.tools.reader.reader-types :refer [string-push-back-reader]]
            [cljs.analyzer :as ana]
            [cljs.compiler :as c]
            [cljs.js :as cljs]
            [cljs.env :as env]
            [cljs.reader :as edn]
            [cljs.nodejs :as nodejs]))

(def st (cljs/empty-state))

(enable-console-print!)
#_(set! *target* "nodejs")

(def cenv (cljs/empty-state))
(def fs (js/require "fs"))
#_(def core (.readFileSync fs "./out/cljs/core.cljs" "utf8"))

;; 3.7s in Node.js with :simple :optimizations
(defn analyze-file [f]
  (let [rdr (string-push-back-reader f)
        eof (js-obj)
        env (ana/empty-env)]
    (binding [ana/*cljs-ns* 'cljs.user
              *ns* (create-ns 'cljs.core)
              r/*data-readers* tags/*cljs-data-readers*]
      (with-compiler-env cenv
        (loop []
          (let [form (r/read {:eof eof} rdr)]
            (when-not (identical? eof form)
              (ana/analyze
                (assoc env :ns (ana/get-namespace ana/*cljs-ns*))
                form)
              (recur))))))))

(defn compile-and-spit [s]
  (let [rdr (string-push-back-reader s)
        eof (js-obj)
        env (ana/empty-env)]
    (binding [ana/*cljs-ns* 'cljs.user
              *ns* (create-ns 'cljs.core)
              r/*data-readers* tags/*cljs-data-readers*]
      (cljs/with-state st
        (loop []
          (let [form (r/read {:eof eof} rdr)]
            (when-not (identical? eof form)
            (print
              (with-out-str
                (c/emit
                  (ana/analyze
                    (assoc env :ns (ana/get-namespace ana/*cljs-ns*))
                    form))))
              (recur))))))))

(defn compile-source [s]
  (with-out-str (compile-and-spit s)))

(defn -main [& args]
  (let [readline (js/require "readline")
        rl (.createInterface readline js/process.stdin js/process.stdout)
        lines (atom [])]
    (.on rl "line" (fn [line] (swap! lines conj line)))
    (.on rl "close" (fn [] (compile-source (apply str (interpose "\n" @lines))) (.exit js/process 0)))))

(defn noop [] nil)

(set! *main-cli-fn* noop)

(aset js/exports "compile" compile-source)