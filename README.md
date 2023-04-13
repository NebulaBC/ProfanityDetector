# Profanity Detector for Java

**How to use this:**

```java
String someText = "Hello, I'm a random text!";

// The given percentage defines the sensitivity. You also need to specify the offensive words for the check
boolean offensive = ProfanityDetector.containsOffensiveWords(someText, percentage, offensivewords);
```