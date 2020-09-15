# mockitoexamples
![alt text](https://github.com/mockito/mockito.github.io/raw/master/img/logo%402x.png)


Basic usages of Mockito and Best Practices against anti patterns

## What is a mock?
According to [**wikipedia**](https://en.wikipedia.org/wiki/Mock_object), they are simulated objects that mimic the behavior of real objects in controlled ways.

In a testing context, sometimes but you need to replace the real implementation by a mock for some reasons:

* Third party integrations (External Services)
* Notifications (to avoid sending push/email/sms... from a testing context)
* Local Events (Publishing/Consuming)
* Local Services (for example, you're testing your API is being Serialize/Deserialize)
* etc...

In a context of TDD, sometimes you have in mind the usage of a dependency, but you only want to define his contract (an Interface) because they will be implemented later.
Well I'm a strong defender of TDD, but this example wasn't developed following TDD because I think it's very straight forward. I have other examples developed using TDD (with mocks and without mocks) at my repo, feel free to [**check them**](https://github.com/geeksusma?tab=repositories)

## How to mock?
![alt text](https://dam.smashmexico.com.mx/wp-content/uploads/2019/07/meme-spiderman.jpg)

In Java projects, there are two very famous libraries:
* [**Mockito**](https://site.mockito.org/) 
* [**Powermock**](https://github.com/powermock/powermock)

Some issues you have to be aware:
* Please choose only one library, the usage of Mockito & Powermock together is not a good idea, since they are not fully compatible each other.
* While Powermock is getting famous from some years ago, Mockito was adopted by SpringBoot, so for a SpringBoot application I recommend the usage of Mockito.
* Some star features of Powermock are considered anti-patterns:
    * Mocking of Static Classes 
    * Mocking of private/protected Methods
    
## Mocking strategies

* :heavy_check_mark: Always Mock Interfaces over implementations
* :x: Never Mock Static Classes
* :x: Never Mock private/protected methods of the same class
* :warning: Using of Argument's Matchers like "Any()" is not a best practice.
* :heavy_check_mark: Ensure your mocks acts as if they were the real ones
* :x: Again, never mix Powermock and Mockito (just choose one library)

And... beyond mocking :information_source::

* Remember the [**FIRST**](https://medium.com/@tasdikrahman/f-i-r-s-t-principles-of-testing-1a497acda8d6) principles while you're writing a test
* In case of checking expected exceptions, please ensure you're checking also the message/attributes of the Exception, not only the type (to avoid false positives)
* As a best practice, initialize your test class in a "setup" method
* Refactor also is welcome in test classes/methods, please avoid duplicate code, follow the [**DRY**](https://dzone.com/articles/software-design-principles-dry-and-kiss) principle.
* ... and more that you'll notice as soon as you [**jump to the code**](src/test/java/es/geeksusma/mockitoexamples/UserDAOTest.java)

## The code
For this example, I created some unit tests to cover a [**DAO**](https://www.baeldung.com/java-dao-pattern) object, it's a typical DAO with two dependencies which basically fetch/post some data from/to a repository, and its also uses a Mapper to transform from a domain entity to a model one, maybe is not the most fancy example, but is fair enough to show some mocking strategies.
As soon as you see the DAO implementation, you'll realize how the repo and the mapper are just interfaces, following the [**I and D principles**](https://en.wikipedia.org/wiki/SOLID) I always prefer to work with interfaces, so then I don't get bothered about low level details, and if it were the case, later once my DAO is finished I could continue coding for the implementations, but having a contract already defined.

You can check how some tests could be deleted because they are redundant each other, this is totally intended, otherwise I could not show to you different ways of mocking.
Also, the API for the DAO could be improved, for example, it makes no so much sense to don't return anything from the persist method (at least, the given id by the database) again this is intended in order to show to you how to deal with void methods.

## Contribute
![alt text](https://miro.medium.com/max/600/1*eCOovrRB8Y0lRL2FIIL33A.jpeg)

PR's and Forks are welcome. Any comment or feedback is also welcome.