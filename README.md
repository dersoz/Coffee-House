# COFFEE HOUSE
A coffee house application. A simple application to demonstrate WildFly Swarm playing nicely with Heroku

# Requirements
* There will be coffee!
* Coffee can be served with a limited set of additives
* There are some discount rules, which are:
    * If the total price is bigger than a certain amount, then a specific amount of tatal price is applied as discount
    * If the customer is buying three or more coffee, then minimum priced coffee is applied as dicount
* There should be at least one coffee
***

## Tech Stack
* Java 8
* WildFly Swarm
* Lombok
* H2 RDBMS
* JaxRS
* Heroku

## Usage
### Local usage
`mvn clean package && java -jar target/kahveci-business-swarm.jar target/kahveci-business.war`
### Heroku
`Procfile` contains the necessary bootstrap command to deploy to Heroku
