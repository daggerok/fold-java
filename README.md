# left fold [![GraalVM](https://github.com/daggerok/fold-java/actions/workflows/ci.yaml/badge.svg)](https://github.com/daggerok/fold-java/actions/workflows/ci.yaml)
Kotlin to the rescue

[fold the universe: reduction with java 8 streams API](https://dzone.com/articles/folding-the-universe-part-iii-java-8-list-and-stre)

<!--I don't know why, but people so confused about that simple operation...-->

_DomainEvents.kt_

```kotlin
@file:JvmName("DomainEvents")

interface DomainEvent {
  val type: Class<out DomainEvent>
    get() = this.javaClass
}

class CounterCreatedEvent : DomainEvent
class CounterIncrementedEvent : DomainEvent
class CounterDecrementedEvent : DomainEvent

fun Aggregate.apply(event: DomainEvent): Aggregate = when (event) {
  is CounterCreatedEvent -> this.on(event)
  is CounterIncrementedEvent -> this.on(event)
  is CounterDecrementedEvent -> this.on(event)
  else -> throw IllegalStateException("unexpected unsupported domain event occur: $event")
}
```

_Some.java_

```java
Optional<Aggregate> foldLeft(Aggregate initialState, DomainEvent... events) {
  if (events.length <= 0) return Optional.empty();
  Aggregate accumulator = initialState;
  for (DomainEvent event : events)
    accumulator = DomainEvents.apply(accumulator, event);
  return Optional.of(accumulator);
}
```

## Run tests

```bash
./gradlew clean test
```

## Links

- [lazy-java](https://github.com/daggerok/lazy-java)
- [java-examples](https://github.com/daggerok/java-examples)
- [learn-jvm](https://github.com/daggerok/learn-jvm)
- [fold-java](https://github.com/daggerok/fold-java)
