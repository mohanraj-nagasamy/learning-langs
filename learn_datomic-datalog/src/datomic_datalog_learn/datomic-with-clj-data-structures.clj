;; https://gist.github.com/mohanraj-nagasamy/387e47ac617d1dfecf4f

(ns datomic-datalog-learn.datomic-with-clj-data-structures
  (:gen-class))

(use '[datomic.api :only [q db] :as d])
(use 'clojure.pprint)

(def forty-two 42)
(def forty-four 44)
;; ?answer binds a scalar

(q '[:find ?answer :in ?answer]
   forty-four)

(def result (q '[:find ?answer :in ?answer]
   forty-four))

(pprint result)


;; of course you can bind more than one of anything
(q '[:find ?last ?first :in ?last ?first]
   "Doe" "John")

;; [?last ?first] binds a tuple
(q '[:find ?last ?first :in [?last ?first]]
   ["Doe" "John"])

;; [?first ...] binds a collection
(q '[:find ?first
     :in [?first ...]]
   ["John" "Jane" "Phineas"])


;; [[?first ?last]] binds a relation
(q '[:find ?first
     :in [[?first ?last]]]
   [["John" "Doe"]
    ["Jane" "Doe"]])

;; a database binding name starts with $ instead of ?
;; any relation with 4-tuples E/A/V/T can act as a database
;; so in Datomic, you can mock a database with a list of lists
(q '[:find ?first
     :in $db
     :where [$db _ :firstName ?first]]
   [
    [1 :firstName "John 1"][2 :firstName "John 2"]
    [1 :lastName "last 1"] [2 :lastName "last 2"]
   ])

(q '[:find ?a ?v
     :where [2 ?a ?v]]
   [
    [1 :firstName "John 1"][2 :firstName "John 2"]
    [1 :lastName "last 1"] [2 :lastName "last 2"] [3 :middleName "middle 3"]
   ])

(q '[:find ?k ?v :where [?k ?v]]
   (System/getProperties))

(q '[:find ?v :where ["java.vendor.url.bug" ?v]]
   (System/getProperties))

(q '[:find  ?pathElem :in [[?k ?v]]
     :where [(.endsWith ?k "path")]
            [(.split ?v ":")[?pathElem]]
            [(.endsWith ?pathElem ".jar")]
    ]
   (System/getProperties))

(q '[:find ?k ?v :in [[?k ?v]]]
   (System/getProperties))

;; same as previous, but omit $db for single-database query
;; any relation with 4-tuples eavt can act as a database
(q '[:find ?first
     :where [_ :firstName ?first]]
   [[1 :firstName "John" 42]
    [1 :lastName "Doe" 42]
    [1 :firstName "John 2" 42]
    [1 :lastName "Doe 2" 42]])

(pprint (q '[:find ?first ?last
             :in $db
     :where [$db _ :firstName ?first] [$db _ :lastName ?last]]
   [[1 :firstName "John" 42]
    [1 :lastName "Doe" 42]
    [2 :firstName "John 2" 42]
    [2 :lastName "Doe 2" 42]]))


;; simple in-memory join, two tuple bindings
(q '[:find ?first ?height
     :in [?last ?first ?email] [?email ?height]]
   ["Doe" "John" "jdoe@example.com"]
   ["jdoe@example.com" 71])

;; simple in-memory join, two relation bindings
;; see next example for a faster approach
(q '[:find ?first ?height
     :in [[?last ?first ?email]] [[?email ?height]]]
   [["Doe" "John" "jdoe@example.com"]
    ["Doe" "Jane" "jane@example.com"]]
   [["jane@example.com" 73]
    ["jdoe@example.com" 71]])


;; same as previous example, but with database expressions
;; runs faster than relation bindings (as of July 2012)
(q '[:find ?first ?height
     :in $a $b
     :where [$a ?last ?first ?email]
            [$b ?email ?height]]
   [["Doe" "John" "jdoe@example.com"]
    ["Doe" "Jane" "jane@example.com"]]
   [["jane@example.com" 73]
    ["jdoe@example.com" 71]])


;; simple in-memory join, two database bindings
(q '[:find ?first ?height
     :in $db1 $db2
     :where [$db1 ?e1 :firstName ?first]
            [$db1 ?e1 :email ?email]
            [$db2 ?e2 :email ?email]
            [$db2 ?e2 :height ?height]]
   [[1 :firstName "John"]
    [1 :email  "jdoe@example.com"]
    [2 :firstName "Jane"]
    [2 :email "jane@example.com"]]
   [[100 :email "jane@example.com"]
    [100 :height 73]
    [101 :email "jdoe@example.com"]
    [101 :height 71]])

;; compare to http://stackoverflow.com/questions/3717939/iterating-and-processing-an-arraylist
(q '[:find ?car ?speed
     :in [[?car ?speed]]
     :where [(> ?speed 100)]]
   [["Stock" 225]
    ["Spud" 80]
    ["Rocket" 400]
    ["Stock" 225]
    ["Clunker" 40]])

;; compare to http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
(->> (q '[:find ?k ?v
          :in [[?k ?v] ...]]
        {:D 67.3 :A 99.5 :B 67.4 :C 67.5})
     (sort-by second))

(clojure.repl/doc pr-str)


(defrecord person [name])

(def people [(person. "Janet Wood")
             (person. "Jack Tripper")
             (person. "Chrissy Snow")])

(pprint (pr-str people))
(pprint (read-string (pr-str people)))


(q '[:find ?e
     :where [?e :age 42]]
   [
     ['sally :age 21]
     ['fred :age 42]
     ['ethel :age 42]
     ['fred :likes :pizza]
     ['sally :likes :opera]
     ['ethel :likes :sushi]
   ])

(q '[:find ?age ?x
     :where [?e :age ?age] [?e :likes ?x]]
   [
     ['sally :age 21]
     ['fred :age 42]
     ['ethel :age 42]
     ['fred :likes :pizza]
     ['sally :likes :opera]
     ['ethel :likes :sushi]
   ])


(def data
  (let [u1 {:db/id (d/tempid :db.part/user) :user/name "Robert"}
        u2 {:db/id (d/tempid :db.part/user) :user/name "Marc"}
        u3 {:db/id (d/tempid :db.part/user) :user/name "Aslam"}
        u4 {:db/id (d/tempid :db.part/user) :user/name "DHH"}
        users [u1 u2 u3 u4]]
    (conj users
          {:db/id       (d/tempid :db.part/user)
           :group/name  "RubyFuza 2014 Speakers"
           :group/users (map :db/id users)})))

(pprint data)


(d/tempid :1)


"asdf"
