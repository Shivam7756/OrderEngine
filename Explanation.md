Data Structures currently used - List
Data Structure to be used for optimized Algo - Priority Queue
Database - MongoDb
Tech Stack - Spring Boot.


Order Matching Algorithm-:

Step 1: First List out all the buy orders and sell orders in two different lists.


Step 2: Now Sort all the buy orders and sell orders on the basis of Instrument Type(Company Type) 
      , after this in case of Buy orders if company name is same for two or more orders, then the order with higher buy price 
      will be inserted at the front of list , in case buy price also matches for two or more orders , the order with lower buyer time will be given more priority.
      Now in case of selling orders , then the order with lower selling price will be given priority ,
      in case selling price also matches for two or more orders , the order with lower selling time will be given more priority , and be present at the front of selling order list.

step 3: Now , the buying order for each company will be compared with selling order of the company type of buying order, and if quantity of current buying order is not 0 and buy price>= selling price , and selling order quantity > 0 then order gets executed , and the number of shares in order will be the minimum of the selling and buy order and will be accordingly subtracted from the current shares left in transaction for that particular buy and sell order.   

step 4: In case the buying order quantity becomes 0 then the next buy order will be executed , and if current selling order quantity becomes 0 , then the next selling order is mapped with current buy order.

step 5: Repeat the above steps untill all buy order is iterated.

Brute Force Time Complexity = O(no of Buy Orders x no of Sell Orders)
Optimized Time Complexity = O(log(no of buy orders))


        
 
