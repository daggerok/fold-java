package com.github.daggerok;

import java.util.Optional;

public class FoldJava {

  public static Optional<CounterAggregate> foldLeft(CounterAggregate initialState, DomainEvent... events) {
    CounterAggregate aggregate = initialState;
    for (DomainEvent event : events)
      aggregate = DomainEvents.apply(aggregate, event);
    return Optional.of(aggregate);
  }

  private FoldJava() { }
}
