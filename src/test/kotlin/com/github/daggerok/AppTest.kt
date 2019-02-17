package com.github.daggerok

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AppTest {

  @Test(expected = IllegalStateException::class)
  fun `enabled counter aggregate should not be enabled again`() {

    CounterAggregate()
        .on(CounterEnabledEvent())
        .on(CounterEnabledEvent())
  }

  @Test(expected = IllegalStateException::class)
  fun `disabled counter should not be incremented`() {

    CounterAggregate()
        .on(CounterEnabledEvent())
        .on(CounterDisabledEvent())
        .on(CounterIncrementedEvent())
  }

  @Test(expected = IllegalStateException::class)
  fun `disabled counter should not be decremented`() {

    CounterAggregate()
        .on(CounterEnabledEvent())
        .on(CounterDisabledEvent())
        .on(CounterDecrementedEvent())
  }

  @Test(expected = IllegalStateException::class)
  fun `disabled counter aggregate should not be disabled again`() {

    CounterAggregate()
        .on(CounterEnabledEvent())
        .on(CounterDisabledEvent())
        .on(CounterDisabledEvent())
  }

  @Test
  fun `should restore Aggregate from domain events collection list and snapshot`() {

    val domainEvents1 = listOf(
        CounterEnabledEvent(),
        CounterIncrementedEvent()
    )

    val domainEvents2 = listOf(
        CounterIncrementedEvent(),
        CounterIncrementedEvent(),
        CounterDecrementedEvent(),
        CounterIncrementedEvent()
    )

    val snapshot = domainEvents1.recreate()
    println("snapshot: $snapshot")
    assertEquals(snapshot.counter, 1)

    val aggregate = domainEvents2.restoreFromSnapshot(snapshot)
    println("aggregate: $aggregate")
    assertEquals(aggregate.counter, 3)
  }

  @Test
  fun `should recreate Aggregate from domain events list`() {

    val domainEvents = listOf(
        CounterEnabledEvent(),
        CounterIncrementedEvent(),
        CounterIncrementedEvent(),
        CounterDecrementedEvent()
    )

    val aggregate = domainEvents.recreate()

    println(aggregate)
    assertEquals(aggregate.counter, 1)
  }

  @Test
  fun `aggregate should replay list of domain events to rebuild it is own state`() {

    val events = listOf(
        CounterEnabledEvent(),
        CounterIncrementedEvent(),
        CounterIncrementedEvent(),
        CounterDecrementedEvent()
    )

    events.forEach { println(it.type) }

    val aggregate1 = CounterAggregate()
    val result1 = events.fold(aggregate1) { acc, domainEvent -> acc.apply(domainEvent) }
    assertEquals(result1.counter, 1)
    println("result1: $result1")

    val aggregate2 = CounterAggregate()
    val result2 = FoldJava.foldLeft(aggregate2, *events.toTypedArray())
    assertNotNull(result2)
    assertEquals(result2.counter, 1)
    println("result2: $result2")

    assertEquals(result1, result2)
  }
}
