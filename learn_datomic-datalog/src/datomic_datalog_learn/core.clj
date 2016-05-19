(ns datomic-datalog-learn.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(use '[datomic.api :only [q db] :as d])
(use 'clojure.pprint)

(def uri "datomic:mem://datomic-test-db")

;; create database
(d/create-database uri)

;; connect to database
(def conn (d/connect uri))


(def data '[[sally :age 21]
 [fred :age 42]
 [ethel :age 42]
 [fred :likes pizza]
 [sally :likes opera]
 [ethel :likes sushi]])


(d '[:find ?e
     :where [?e :age 42]] data)

(def data-2 '[[-100 :lang/name "Visi"]
    [-200 :lang/name "Ioke"]
    [-300 :lang/name "Frink"]
    [-100 :lang/url "http://Visi"]
    [-200 :lang/url "http://Ioke"]])

(d '[:find ?name ?url
     :where [?language :lang/website ?name ?url]]
   )
