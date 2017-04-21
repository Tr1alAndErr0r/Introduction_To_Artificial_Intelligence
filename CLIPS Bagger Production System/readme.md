Suppose that Robbie has been hired to bag groceries in a grocery store. Because he knows little
about bagging groceries, he approaches his new job by creating Bagger, a rule-based production system
that decides where each item should go.

Bagger should be designed to take four steps:  
1. The check-order step: Bagger analyzes what the customer has selected, looking over the
groceries to see whether any items are missing.  
2. The bag-large-items step: Bagger bags the large items, taking care to put the big bottles in first.  
3. The bag-medium-items step: Bagger bags the medium items, taking care to put frozen ones in
freezer bags.  
4. The bag-small-items step: Bagger bags the small items.

Some other rules are also considered:
1. Each bag can only take one-sized item (i.e. no small items in a bag with medium items, etc.).  
2. Each bag can only contain up to 6 large items.  
3. Each bag can only contain up to 12 medium items.  
4. Each bag can only contain up to 18 small items.  

This rule-based system is implemented in CLIPS.
