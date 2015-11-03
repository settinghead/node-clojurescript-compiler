(ns node-cljs-compiler.jar
  (:require-macros [cljs.core]
                   [cljs.env.macros :refer [ensure with-compiler-env]]
                   [cljs.analyzer.macros :refer [no-warn]])
  (:require [cljs.pprint :refer [pprint]]))

(enable-console-print!)

(def child-process (js/require "child_process"))

(defn compile [] nil)

(defn noop [] nil)
(set! *main-cli-fn* noop)

(aset js/exports "compile" compile)
