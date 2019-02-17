package com.github.daggerok;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CounterAggregate {

  private final List<DomainEvent> events = new CopyOnWriteArrayList<>();

  private boolean enabled;
  @Getter private int counter;

  /* commands handling */

  public CounterAggregate on(CounterCreatedEvent event) {
    if (enabled) throw new IllegalStateException("already created");
    add(event);
    return applyFor(event);
  }

  public CounterAggregate on(CounterIncrementedEvent event) {
    if (!enabled) throw new IllegalStateException("disabled");
    add(event);
    return applyFor(event);
  }

  public CounterAggregate on(CounterDecrementedEvent event) {
    if (!enabled) throw new IllegalStateException("disabled");
    add(event);
    return applyFor(event);
  }

  public CounterAggregate on(CounterDisabledEvent event) {
    if (!enabled) throw new IllegalStateException("disabled");
    add(event);
    return applyFor(event);
  }

  /* events handling */

  public CounterAggregate applyFor(CounterCreatedEvent event) {
    enabled = true;
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

  public CounterAggregate applyFor(CounterDisabledEvent event) {
    enabled = false;
    return this;
  }

  /* private API: DRY code */

  private void add(DomainEvent event) {
    events.add(event);
  }
}
