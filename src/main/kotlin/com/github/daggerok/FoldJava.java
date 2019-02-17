package com.github.daggerok;

import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FoldJava {

  public static Optional<CounterAggregate> foldLeft(CounterAggregate initialState, DomainEvent... events) {
    CounterAggregate aggregate = initialState;
    for (DomainEvent event : events)
      aggregate = DomainEvents.apply(aggregate, event);
    return Optional.of(aggregate);
  }
}
