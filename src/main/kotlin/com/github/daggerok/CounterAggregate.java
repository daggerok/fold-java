package com.github.daggerok;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class CounterAggregate {
  private final List<DomainEvent> events;

  public CounterAggregate() {
    events = new CopyOnWriteArrayList<>();
  }

  private int counter;

  /* command handling */

  public CounterAggregate on(CounterCreatedEvent event) {
    add(event);
    return applyFor(event);
  }

  public CounterAggregate on(CounterIncrementedEvent event) {
    add(event);
    return applyFor(event);
  }

  public CounterAggregate on(CounterDecrementedEvent event) {
    add(event);
    return applyFor(event);
  }

  /* event handling */

  public CounterAggregate applyFor(CounterCreatedEvent event) {
    counter = 0;
    return this;
  }

  public CounterAggregate applyFor(CounterIncrementedEvent event) {
    counter += 1;
    return this;
  }

  public CounterAggregate applyFor(CounterDecrementedEvent event) {
    counter -= 1;
    return this;
  }

  /* private API */

  private void add(DomainEvent event) {
    events.add(event);
  }

  /* instance public API */

  public int getCounter() {
    return counter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CounterAggregate that = (CounterAggregate) o;
    return counter == that.counter &&
        events.equals(that.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(events, counter);
  }

  @Override
  public String toString() {
    return "CounterAggregate {" +
        "'amountOfEvents': '" + events.size() +
        "', 'counter': '" + counter +
        "'}";
  }
}
