App Engine Template environment for IDE Eclipse, Jersey, Maven, Jackson, Objectify, Oauth2 assembled and extending apache 2.0 code base.

Loosely derived from Googles guestbook maven framework, much of which has been updated or removed. Merged with code from n3phele core to provide basic rest exposure of resources backed with objectify, jax-rs style of rest services definition, and security.

Requires [Apache Maven](http://maven.apache.org) 3.1 or greater, and JDK 7+ in order to run.

To build, run

    mvn package

Building will run the tests, but to explicitly run tests you can use the test target

    mvn test

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver

For further information, consult the [Java App Engine](https://developers.google.com/appengine/docs/java/overview) documentation.

To see all the available goals for the App Engine plugin, run

    mvn help:describe -Dplugin=appengine