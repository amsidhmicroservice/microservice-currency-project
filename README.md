# URL's
1) Config Server:
   http://localhost:8888/currency-exchange/test
   http://localhost:8888/currency-conversion/test
2) CurrencyExchange Service
   http://localhost:8181/currency-exchange/USD/to/INR
3) CurrencyConversion Service
   http://localhost:8282/currency-conversion/from/USD/to/INR/quantity/13
4) Eureka Discovery URL
   http://localhost:8761/


 # URLs for next Lecture
Some of these URLs may be complex to write by hand:
Refer back to here if you have problems in the next steps.


Initial

- http://localhost:8765/CURRENCY-EXCHANGE/currency-exchange/from/USD/to/INR

- http://localhost:8765/CURRENCY-CONVERSION/currency-conversion/from/USD/to/INR/quantity/10

- http://localhost:8765/CURRENCY-CONVERSION/currency-conversion-feign/from/USD/to/INR/quantity/10



Lower Case

- http://localhost:8765/currency-exchange/currency-exchange/from/USD/to/INR

- http://localhost:8765/currency-conversion/currency-conversion/from/USD/to/INR/quantity/10

- http://localhost:8765/currency-conversion/currency-conversion-feign/from/USD/to/INR/quantity/10



Custom Routes

- http://localhost:8765/currency-exchange/from/USD/to/INR

- http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10

- http://localhost:8765/currency-conversion-feign/from/USD/to/INR/quantity/10

- http://localhost:8765/currency-conversion-new/from/USD/to/INR/quantity/10  