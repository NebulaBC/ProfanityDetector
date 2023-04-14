[![](https://jitpack.io/v/Uroria/ProfanityDetector.svg)](https://jitpack.io/#Uroria/ProfanityDetector)

## Profanity Detector for Java applications

**How to use this:**

```java
String someText = "Hello, I'm a random text!";

// The given percentage defines the sensitivity in decimal. You also need to specify the offensive words for the check
boolean offensive = ProfanityDetector.containsOffensiveWords(someText, percentage, offensivewords);
```
