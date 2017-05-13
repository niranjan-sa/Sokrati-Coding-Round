# Assumptions
* Dates are sorted as logs record in chronological ordered. Modification provided for unordered entries
* All dates are in same valid format
* Visits on the same days are not considered while counting consecutive 4 days

# Test Cases covered
(file log.txt provided contains all the test cases)
* No consecutive 4 day visits
* 3 consecutive day visits
* Only one customer consecutive visitor
* Many customeres consecutive visitors

# Output (for the given file log.txt)
The following customers visited the site for 4 consecutive days :-   
[3, 9]
