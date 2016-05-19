(defn fac [n]
  (if (= n 1)
    1
    (* n (fac (- n 1)))))

(fac 3)


(defn add-3 [n] (+ n 3))

(def add-4 (fn [n] (+ n 4)))

;; (def add-5 (#(n) (+ n 5))))

(add-3 3)
(add-4 3)

(def ^:dynamic my-value 10)

(defn add-to-my-value [n]
  (+ n my-value))

(add-to-my-value 2)

(binding [my-value 20]
  (add-to-my-value 2))

(binding [my-value 20]
  (add-to-my-value 2))

(def my-numbers [1 2 3])

(apply - my-numbers)


(def my-vec [11 22 33 44])

(first my-vec)
(rest my-vec)


(def some-numbers (ref #{11 22 33}))

(println @some-numbers)

(dosync (alter some-numbers disj 22))

(dosync (alter some-numbers conj 44))

(reduce * 10 [1 2 3 4])

(reduce + [1 2 3 4 5])
(reduce + [])
(reduce + 1 [])

(reduce + 1 [2 3])

(reduce conj #{} [:a :b :c])

(defn mohan-concat [ds1 list1]
  (conj ds1 list1))

(reduce conj #{} [:a :b :c])

(reduce mohan-concat {} {:a :aaaa :b :bbbb :c :ccccc})
(reduce conj {} {:a :aaaa :b :bbbb :c :ccccc})
