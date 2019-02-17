# java fold implementation
I don't know why, but people so confused about that simple operation... 

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

fun CounterAggregate.apply(event: DomainEvent): CounterAggregate = when (event) {
  is CounterCreatedEvent -> this.on(event)
  is CounterIncrementedEvent -> this.on(event)
  is CounterDecrementedEvent -> this.on(event)
  else -> throw IllegalStateException("unexpected unsupported domain event occur: $event")
}
```

```java
Optional<Aggregate> foldLeft(Aggregate initialState, DomainEvent... events) {
  Aggregate accumulator = initialState;
  for (DomainEvent event : events)
    accumulator = DomainEvents.apply(accumulator, event);
  return Optional.of(accumulator);
}
```
