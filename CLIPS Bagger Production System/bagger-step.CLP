(deffacts initial-facts
  (step check-order))

(defrule B2
  ?step-fact <- (step check-order)
=>
  (retract ?step-fact)
  (assert (step bag-large-items)))

(defrule B6
  ?step-fact <- (step bag-large-items)
=>
  (retract ?step-fact)
  (assert (step bag-medium-items)))

(defrule B10
  ?step-fact <- (step bag-medium-items)
=>
  (retract ?step-fact)
  (assert (step bag-small-items)))

(defrule B13
  ?step-fact <- (step bag-small-items)
=>
  (retract ?step-fact)
  (assert (step done)))
  