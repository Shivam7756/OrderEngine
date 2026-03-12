Data Structures used - List
Database - MongoDb
Tech Stack - Spring Boot.


Order Matching Algorithm-:

Step 1: First List out all the buy orders and sell orders in two different lists.
Step 2: Now Sort all the buy orders and sell orders on the basis of Instrument Type(Company Type) 
      , after this in case of Buy orders if company name is same for two or more orders, then the order with higher buy price 
      will be inserted at the front of list , in case buy price also matches for two or more orders , the order with lower buyer time will be given more priority.
      Now in case of selling orders , then the order with lower selling price will be given priority ,
      in case selling price also matches for two or more orders , the order with lower selling time will be given more priority.
