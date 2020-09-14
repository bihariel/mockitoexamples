# mockitoexamples
![alt text](https://github.com/mockito/mockito.github.io/raw/master/img/logo%402x.png)


Basic usages of Mockito and best practices against anti patterns

## What is a mock?
According to [**wikipedia**](https://en.wikipedia.org/wiki/Mock_object), they are simulated objects that mimic the behavior of real objects in controlled ways.

In a context of testing, sometime you need to mock an Object which is used, but you need to replace the real implementation by a mock for some reasons as:

* Third party integrations (External Services)
* Notifications (to avoid sending push/email/sms... from a testing context)
* Local Events (Publishing/Consuming)
* etc...

In a context of TDD, sometimes you have in mind the usage of a dependency, but you only want to define his contract (an Interface) because they will be implemented later.

## How to mock?
![alt text](https://dam.smashmexico.com.mx/wp-content/uploads/2019/07/meme-spiderman.jpg)

In Java projects, there are two very famous libraries:
* [**Mockito**](https://site.mockito.org/) 
* [**Powermock**](https://github.com/powermock/powermock)

Some issues you have to be aware:
* Please choose only one library, the usage of Mockito & Powermock together is not a good idea, since they are not fully compatible each other
* While Powermock is getting famous from some years ago, Mockito was adopted by SpringBoot, so for a SpringBoot application is recommended the usage of Mockito.
* Some "star features" for Powermock, are considered anti-patterns, like:
    * Mocking Static Classes 
    * Mocking private/protected Methods
    
## Mocking strategies

* Always Mock Interfaces over implementations
* Never Mock Static Classes
* Never Mock private/protected methods of the same class
* Using of Argument's Matchers like "Any()" is not a best practice.
* Ensure your mocks acts as if they were the real ones
* Never mix Powermock and Mockito (just choose one library)

And... beyond mocking:

* Remember the [**FIRST**](https://medium.com/@tasdikrahman/f-i-r-s-t-principles-of-testing-1a497acda8d6) principles while you're writing a test
* In case of checking expected exceptions, please ensure you're checking also the message/attributes of the Exception, not only the type (to avoid false positives)
* As a best practice, initialize your test class in a "setup" method
* Refactor also is welcome in test classes/methods, please avoid duplicate code, follow the [**DRY**](https://dzone.com/articles/software-design-principles-dry-and-kiss) principle.
* ... and more that you'll notice as soon as you [**jump to the code**](file://src/main/test/java/es/geeksusma/mockitoexamples/UserDAOTest.java)




