# Hibernate_And_Spring_Data
Spring Data SoftUni February 2020
APP_INTRODUCTION

1.	Initial Setup 

Create a new database called “MinionsDB”, where we will keep information about our minions and villains. 
For each minion you must keep information about its name, age and town.  Each town has name and information about the country it is located in. Villains have name and evilness factor (good, bad, evil, super evil). Each minion can serve to several villains and each villain can have several minions to serve him. Fill all the tables with at least 5 records each.
Write a program that connects to your localhost server.

2.	Get Villains’ Names

Write a program that prints on the console all villains’ names and their number of minions. Get only the villains who have more than 15 minions. Order them by number of minions in descending order.

3.	Get Minion Names

Write a program that prints on the console all minion names and their age for given villain id. For the output, use the formats given in the examples.

4.	Add Minion

Write a program that reads information about a minion and its villain and adds it to the database. In case the town of the minion is not in the database, insert it as well. In case the villain is not present in the database, add him too with default evilness factor of “evil”. Finally, set the new minion to be servant of the villain. Print appropriate messages after each operation – see the examples.

5.	Change Town Names Casing

Write a program that changes all town names to uppercase for a given country. Print the number of towns that were changed in the format provided in examples. On the next line print the names that were changed, separated by coma and a space.

6.	*Remove Villain

Write a program that receives an ID of a villain, deletes him from the database and releases his minions from serving him. As an output print the name of the villain and the number of minions released. Make sure all operations go as planned, otherwise do not make any changes to the database. For the output use the format given in the examples.

7.	Print All Minion Names

Write a program that prints all minion names from the minions table in order first record, last record, first + 1, last – 1, first + 2, last – 2… first + n, last – n. 
1	3	5	7	9	10	8	6	4	2
									

8.	Increase Minions Age

Read from the console minion IDs, separated by space. Increment the age of those minions by 1 and make their names title to lower case. Finally, print the names and the ages of all minions that are in the database. See the examples below.

9.	Increase Age Stored Procedure

Create a stored procedure usp_get_older (directly in the database using MySQL Workbench or any other similar tool) that receives a minion_id and increases the minion’s years by 1. Write a program that uses that stored procedure to increase the age of a minion, whose id will be given as an input from the console. After that print the name and the age of that minion.
