(defproject datomic-datalog-learn "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.datomic/datomic-free "0.9.5067"]]
  :main ^:skip-aot datomic-datalog-learn.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
