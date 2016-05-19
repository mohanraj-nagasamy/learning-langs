(println "Hello, World")

;; Data

(defn great
  ([your-name]
  (str "Hello, ", your-name))

  ([msg, your-name]
  (str msg, your-name)))

(great "Mohan")

;; Code Part 1

(def v [1 2 3])

(def trusted-v (with-meta v {:source :trusted}))

;(def ^{:source :trusted} trusted-v v)

(meta trusted-v)

(:source (meta trusted-v))

(:source (meta v))

(= v trusted-v)

;; (source max)

;; Code Part 2

(str (java.util.Date. ))
(str (new java.util.Date))

(str (Math/PI))

;; the verb always comes first ex: +, method name

(str (.getMonth (java.util.Date.)))

;; destructure

(defn next-fib-pair
  [pair]
  [(second pair) (+ (first pair) (second pair))])

(take 5 (next-fib-pair [5 8]))

;;(iterate (next-fib-pair [5 8]))

(defn next-fib-pair-with-destr
  [[a b]]
  [b (+ a b)])

(take 5 (next-fib-pair-with-destr [5 8]))

(defn fibs []
  (map first
   (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

(defn format-name
  [person]
  (clojure.string/join " " [(:salutation person)
                 (:first-name person)
                 (:last-name person)]))
(format-name  {:salutation "Mr." :first-name "John" :last-name "Doe"})

(defn format-name
  [name]
  (let [{salutation :salutation
         first-name :first-name
         last-name :last-name}
        name]
    (clojure.string/join " " [salutation first-name last-name])))

(defn format-name
  [{:keys [salutation first-name last-name]}]
    (clojure.string/join ", " [salutation first-name last-name]))

(format-name  {:salutation "Mr." :first-name "John" :last-name "Doe keys"})

(let [[a b c & d :as e] [1 2 3 4 5 6 7]]
  [a b c d e])

(let [[[x1 y1] [x2 y2]][[1 2] [3 4]]]
  [x1 y1 x2 y2])


(range 10)

(map inc (range 10))

(filter odd? (map inc (range 10)))

(reduce + (filter odd? (map inc (range 10))))

(->> (range 10)
     (map inc)
     (filter odd?)
     (reduce +))

(defn blank? [s] (every? #(Character/isWhitespace %) s))

(blank? "")

println

;; Functions

(drop 2 [11 22 33 44 55 66])
(take 9 (cycle [1 2 3]))
(take 9 (range 19 100))

(interleave [:a :b :c :d :e :f] [1 2 3 4 5])
(zipmap [:a :b :c :d :e :f] [1 2 3 4 5])
;; (interleave [:a :b :c :d :e :f] [1 2 3 4 5])
(partition 3 (range 10))
(partition-all 3 (range 10))

(map vector  [:a :b :c :d :e :f] [1 2 3 4 5])

(apply str (interpose \@ "asdf"))

(reduce + (range 10))

(for [x (range 2 5) y (range 3)] [x y] )

(take 10 (for [x (range 10000) y (range 100000)
              :while (> x y)]
          [x y]))


;; Vector

(def v [42 :rabbit [11 22 33]])

(v 1)
(v 0)

(peek v)
(pop v)

(subvec v 1)

(contains? v 0)
(contains? v 42)

;; Maps

(def m {:a 11 :b 22 :c 33})

(m :b)

(:b m)

(keys m)

(assoc m :d 44 :c 33333)
(dissoc m :d)

(merge-with + m {:a 2 :b 3})

;; Nested Structures

(def jdoe {:name "John Doe",
           :address {:zip 22323}})

(get-in jdoe [:address :zip])

(assoc-in jdoe [:address :zip] 889899)

(update-in jdoe [:address :zip] inc)

;; Sets


(use 'clojure.set)

(def colors #{"red" "green" "blue"})
(def moods #{"happy" "blue"})

(disj colors "red")

(difference colors moods)

(intersection colors moods)

(union colors moods)

(def v [1 2 3])

(def l '(1 2 3))

(conj v 42)
(conj l 42)

(into v [99 :bottle])
(into l [99 :bottle])


;; Abstractions


(def mohan {:fname "mohan"
            :lname "nagasamy"
            :address {:street "Some South"
                      :city "slc"
                      :zip 84115}})

(:lname mohan)

;; Thread first operator

(-> mohan :address :city)

(set! *print-length* 1000)
(set! *print-level* 1000)

(assoc mohan :fname "mohanraj")
(assoc-in mohan [:address :zip] 2342342)
(update-in mohan [:address :zip] inc)



;; ...to Records! (object oriented)
;; defrecord for infomation
;; deftype for mechanics

(defrecord Person [fname lname address])
(defrecord Address [street city state zip])

(def mohan (Person. "mohan" "nagasamy"
                    (Address. "31231 S" "SLC" "UT" 84115)))
(:lname mohan)

;; Protocols

(defprotocol AProtocol
  "A doc string for AProtocol abstration"
  (bar [a b] "bar docs")
  (baz [a] "baz docs"))


(defrecord Bar [a b c] AProtocol
  (bar [this b] "Bar Bar")
  (baz [this] (str "Bar baz " c)))

(def b (Bar. 5 6 7))

(baz b)
(class b)
(supers (class b))

(extend-type String AProtocol
  (bar [s s2] (str s s2))
  (baz [s] (str "baz " s)))

(baz "a")

;; deftype

(deftype Bar [a b c])

(def o (Bar. 1 2 3))

(.b o)
(class o)
(supers (class o))

;; Rock Paper Scissors

(defprotocol Player
  (choose [p])
  (update-strategy [p me you]))

;; Concurrency

(def counter (atom 0))

(swap! counter + 10)

(deref counter)

(print @counter)

;; JVM Interop

(class str)

