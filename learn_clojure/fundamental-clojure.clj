



(require 'clojure.string)
;; (require 'clojure.string)

(+ 1 2)

(with-meta [1 2 3] {:example true})

(meta (with-meta [1 2 3] {:example true}))


((fn [x1 x2 x3] (+ x1 x2 x3 5)) 5 5 5)

(#(+ %1 %2 %3 5) 5 5 5)



(doc when)
(doc def)
(doc var)
(doc defn)

(find-doc "sequential?")
(print (apropos "map"))
(source take)
(source when)
(source defn)

(dir clojure.repl)
(dir clojure.core)
(print (keys (ns-publics 'clojure.core)))
(use '[clojure.string :only (join)])
(print (join ", " (keys (ns-publics 'clojure.core))))

(ns-imports (the-ns 'user))
(ns-refers (the-ns 'user))

(print (all-ns))
;; Funcitons

;; def, fn
(def my-print (fn [msg] (println (str "my-print" msg))))
(fn my-print-1 [msg] (println (str "my-print-1" msg)))

(my-print1 "Hello World")

;; let
(let [a 10
      b 20]
  (+ a b))

(let [a (let [a 11 b 20]  (+ a b)) b 20] (+ a b))

(defn messenger [msg]
  (let [a 5
       b 10
       c (capitalize msg)
;;        c (.toUpperCase msg)
        ]
    (println a b c)))

(messenger "test")

;; Multi-arity functions

(defn mohan-adder
  ([x] (+ x 10))
  ([x y] (+ x y)))

(mohan-adder 10 (mohan-adder 10))

(defn say-hello
  ([] (println "Hello World"))
  ([msg] (println msg)))

(say-hello "Mic testing")

;; Variadic functions

(defn say-hello
  ([] (println "Hello World"))
  ([msg & msgs] (println msg msgs)))

(say-hello "Hello" "Mic")

;; apply - Invoke functions on arguments (unpack remainig args)


(defn say-hello
  ([] (println "Hello World"))
  ([msg & msgs] (println msg (apply str msgs))))

(say-hello "Hello" "Mic" "Test")

(defn say-hello
  ([] (println "Hello World"))
  ([msg & msgs] (println msg (map #(str " " % " ")) msgs)))


(let [a 1
      b 2
      more `(3 4)]
  (str a b more))

;; (apply str " " a b more) same as => (str a b more)

(defn say-hello [msg & msgs] (apply print msg msgs))
(say-hello "Hello" "World" "class" "class1")

(defn say-hello [& msgs] (apply print msgs))
(say-hello)

(let [nums `(1 2 3 4)]
  (apply + nums))

(let [nums `("1" "2" "3" "4")]
  (apply str nums))

(apply str #{"str1" "str2" "str3"})

(apply map vector [[:a :b] [:c :d]])

(vector [:a :b] [:c :d])

(join ", " [1 2 3])


;; Closure

(defn messenger-builder [greeting]
  (fn [who] (println greeting who)))

(def hello-er (messenger-builder "Hello"))

(hello-er "World")

;; Java Methods vs Functions

(def my-length (fn [obj] (.length obj)))
(my-length "asdf")

((fn [obj] (.length obj)) "asdfasdfsaf")
(#(.length %) "asdfa")

;; Namespace - Load, Alias, Refer, Import
;; require - will load
(require 'clojure.set)

(clojure.set/union #{1 2} #{3 4})

;; require - both load & alias
(require '[clojure.set :as set1] )
(set1/union #{1 2} #{3 4})

;; Use - not in general. But useful in REPL
;; Use - with :only - brings sysmbols ones you specify

(use '[clojure.string :only (join)])
(join ", " [1 2 3])

Delete me with clojure.java.io
(use 'clojure.java.io)

(ns-publics 'clojure.java.io)
(keys (ns-publics 'clojure.java.io))

(dir clojure.java.io)
(doc delete-file)

(delete-file "/Users/mohanraj.nagasamy/Desktop/Deleteme.txt")

;; use, require will take optional flags to force reload
(require 'foo.bar :reload)
(require 'foo.bar :reload-all)

;; Import - Makes java classes available

(import (java.io FileReader File))

;; Creates namespaces and loads, aliases what you need

(ns foo.bar.baz
  (:require [clojure.set :as s])
  (:use [clojure.java.io :only (delete-file)])
  (:import (java.io File Writer)))

*ns*

(s/union #{1 2} #{3 4})

(defn do-union [& sets]
  (apply s/union sets))

(do-union #{1 2} #{3 4} #{5 6})

(defn delete-old-file [& files]
  (doseq [f files]
    (delete-file f)))



(defn my-private [] ^:private (println "secrets-1"))
(defn- my-private [] (println "secrets-2"))

(my-private)

;; Namespaces are first class objets

(the-ns 'clojure.core)
(source ns-map)
(println (ns-map 'clojure.core))
(ns-interns 'clojure.core)
(ns-publics 'clojure.core)
(ns-refers 'clojure.core)
(ns-aliases 'clojure.core)
(clojure.repl/dir clojure.core)

;; Clojure Collections

;; List Examples
()
(list 1 2 3)
'(1 2 3)
'(1 2 3 (+ 1 2))
(conj '(2 1) 3 4 5)
(conj '(2 3) 1)
(nth '(11 22 33) 2)

;; Vectors - Indexed, random-access, array-like
[]
[1 2 3]
'[1 2 3 (+ 1 2 3)]
(vector 1 2 3)
(vec '(1 2 3 4))
(conj [3 2] 1)
(nth [11 22 33] 2)
(conj [1 2] 3)

;; Map - associative

{}
{:a 1 :b 2}

(def my-map {:a 111 :b 222})

(:a my-map)
(my-map :a)

(assoc my-map :c 333)
(assoc my-map :c 333 :d 444)
(dissoc my-map :b)
(dissoc my-map :b :a)

(conj my-map [:c 333])
(conj my-map {:c 333})

(conj my-map {:c 333 :d 444})


;; Nested Access

(def jdoe {:name "John Doe" :address {:zip 84115}})
(get jdoe :name)
(:name jdoe)
(get jdoe :age)

(get-in jdoe [:address])
(get-in jdoe [:address12])
(get-in jdoe [:address :zip])
(get-in jdoe [:address :zip12] :not-found)
(assoc-in jdoe [:address :zip] 841111115)
(update-in jdoe [:address :zip] inc)
(update-in jdoe [:address :zip] +)
(update-in jdoe [:address :zip] #(str "-" % "-"))
(update-in jdoe [:address :zip] #(+ % 10))
(assoc jdoe :age 22)

;; Sets - of distinct values

#{}
#{:a :b}
(def my-set #{:a :b})
(my-set :a)
(:a my-set)

(conj #{} :a :b)
(contains? my-set :a)
(contains? my-set :aa)

(require '[clojure.set :as set] )
(set/union #{:aa} my-set)
(set/difference my-set #{:a})
(set/intersection my-set #{:a})

;; Destructuring

;; Sequential Destructuring

(def stuff [7 8 9 10 11])
(def stuff '(7 8 9 10 11))
(let [[a b c] stuff]
  (list (+ a b) (+ b c)))

(let [[a b c d e f g] stuff]
  (list d e f g))

(let [[a & others] stuff]
  (println a)
  (println others))

(let [[_ & others] stuff]
  (println _)
  (println others))

(defn rest-numbers [[_ & rest-numbers]]
  rest-numbers)

(rest-numbers stuff)

;; Associative Destructuring

(def m {:aa 77777 :bb 888888})

(let [{a :aa b :bb} m]
  [a b])

(let [{:keys [aa bb cc]} m]
  [aa bb cc])

(let [{:keys [aa bb cc]
       :or {cc :not-found}} m]
  [aa bb cc])

(def names ["mohan", "raj", "naga"])
(let [[_ _ _] names] _)

;; Named Arguments

(defn game [planet & {:keys [human-players computer-players]}]
  (println "Total Players:" (+ human-players computer-players) " in " planet))

(game "Mars" :human-players 10 :computer-players 3)
;; (game "Mars" {:human-players 10 :computer-players 3})

(defn draw-point [& {:keys [x y z]
                     :or {x 0 y 0 z 0}}]
  (println "x: " x " y: " y " z: " z))

(draw-point :z 999 :x 888 :y 777)
(draw-point)

;; Sequences

(class (seq [1 2 3])) ;; Sequences are not lists

(seq [])
(seq {})
(seq ())
(seq [{}])

(class (seq [1 2]))
(class (seq '(1 2)))

(seq [1 2 3])
(seq {:a 1 :b 2 :c 3})

(first [1 2 3])
(first {:a 1 :b 2 :c 3})

(second [1 2 3])
(second {:a 1 :b 2 :c 3})

(rest [1 2 3])
(rest {:a 1 :b 2 :c 3})

(cons 4 [1 2 3])
(cons [:d 4] {:a 1 :b 2 :c 3})

;; Sequences - are lazy

(range 1 40)
(def a-range (range 1 100))
(first a-range)
(second a-range)
(rest a-range)

(range)
(map #(* % %) (range))
(filter even? (range))
(filter odd? (range))

(apply str (interpose ", " (range 3)))

;; Using a Seq

(reduce + (range 4))
(into #{} "hellow")
(into {} [[:x 1] [:y 2]])
(some {:aaa 111 :bbb 222} [:aaa nil :bbb])
;; (filter {:aaa 111 :bbb 222} [:aaa nil :bbb])

(set! *print-length* 10)
(iterate (fn [[a b]]
         [b (+ a b)])
         [0 1])
(map first (iterate (fn [[a b]]
         [b (+ a b)])
         [0 1]))

(def fibs (map first (iterate (fn [[a b]]
         [b (+ a b)])
         [0 1])))

(take 5 fibs)

;; Flow Control

;; Statement vs Expressions
;; Everthing in clojure is an Expressions
;; Flow Control operatos are Expressions too

(if true :truthy :falsey)
(if (Object.) :truthy :falsey)
(if [] :truthy :falsey)
(if () :truthy :falsey)

(if false :truthy :falsey)
(if nil :truthy :falsey)
(if (seq []) :truthy :falsey)

;; Be careful with Boolean objects
(if Boolean/FALSE :truthy :falsey)
(def eval-false (Boolean. false))

(= eval-false false)
(if eval-false :truthy :falsey)

(str "2 is " (if (even? 2) "even" "odd"))

(if (true? false) "Impossible!")

;; If/do

(if (even? 5)
  (do (println "Even") true)
  (do (println "Odd") false))

;; if-let

(if-let [x (even? 3)]
  (println "x: " x)
  (println "Some Odd Value"))

(defn show-evens [coll]
  (if-let [evens (seq (filter even? coll))]
  (do (println "The evens are : " evens) evens)
  (do (println "There were no evens"))))

(show-evens (range 10))
(show-evens [1 3 5])

;; Cond - series of tests and expressions

(cond
 test1 expr1
 test2 expr2
 :else else-expr)

(let [x 5]
  (cond
   (< x 2) "x is < than 2"
   (< x 10) "x is < than 10"))
(let [x 1]
  (cond
   (< x 2) "x is < than 2"
   (< x 10) "x is < than 10"))
(let [x 11]
  (cond
   (< x 2) "x is < than 2"
   (< x 10) "x is < than 10"))
(let [x 11]
  (cond
   (< x 2) "x is < than 2"
   (< x 10) "x is < than 10"
   :else "x is not < 2 and < 10"))

;; Condp - Cond with shared predicate
(defn foo [x]
  (condp = x
    5 "x is 5"
    10 "x is 10"
    "x isn't 5 or 10"))
(foo 11)

(defn foo [x]
  (condp > x
    2 "x < 2"
    10 "x < 10"
    "x isn't < 2 or < 10"))
(foo 1)
(foo 8)
(foo 11)

;; Case
(defn foo [x]
  (case x
    2 "x = 2"
    10 "x = 10"
    "x isn't 2 or 10"))
(foo 2)
(foo 10)
(foo 11)

(defn str-binary [n]
  (cond
   (= n 0) "zero"
   (= n 1) "one"
   :else "unknown"))
(defn str-binary [n]
  (condp = n
   0 "zero"
   1 "one"
   "unknown"))
(defn str-binary [n]
  (case n
   0 "c-zero"
   1 "c-one"
   "c-unknown"))

(str-binary 0)
(str-binary 1)
(str-binary 2)

;; Recursion n iteration

;; doseq - iterate over sequence; Similar to java's foreach

(doseq [n (range 3)]
  (println n) n)

;; dotimes - Evaluate expression n times
(dotimes [i 5]
  (println i))

;; while - Evaluate expression while the condition is true
;; for - List comprehensions, NOT a for-loop
(for [x [0 1]
      y [0 0]]
  [x y])

;; loop - Funcitonal looping construct
;; recur - re-executes loop with new binding
(loop [i 0]
  (if (< i 10)
    (do (println "i : " i)
      (recur (inc i)))
    i))
;; defn/recur - fn arguments are bindings
(defn increase [i]
  (if (< i 10)
    (do (println "I:" i)
      (recur (inc i)))
    i))
(increase 5)
(increase 50)

;; recur - for recursion

(defn factorial
  ([n] (factorial 1N n))
  ([accum n]
   (if (zero? n)
     accum
     (factorial (* accum n) (dec n)))))

(defn factorial
  ([n] (factorial 1N n))
  ([accum n]
   (if (zero? n)
     accum
     (recur (* accum n) (dec n)))))

(factorial 500000)

;; Exception handling
(try (/ 1 1)
  (catch ArithmeticException e (println (.getMessage e))(.getMessage e))
  (finally (println "cleanup")))

;; throwing Exception

(try
  (throw (Exception. "something went wrong"))
  (catch Exception e (.getMessage e)) )

;; with-open - try-with-resources
(require '[clojure.java.io :as io])
(with-open [f (io/writer "/Users/mohanraj.nagasamy/temp/clj-temp.file")]
  (.write f "some text"))


