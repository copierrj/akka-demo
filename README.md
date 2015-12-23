# akka-demo

A small Akka application to demonstrate a few characteristics of a typical Akka application.

The application consists of a several actors:

- EchoService: responds to every message with a random delay.
- MeasureService: measure the time to it takes for another actor to respond to a message.
- MeasureHandler: helper actor for MeasureService.
- MeasureAverageService: measure the average time to it takes for another actor to respond to a message.
- PrintService: prints received messages and sends notifications to actors waiting for a specific number of lines being printed.  