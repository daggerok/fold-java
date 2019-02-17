package com.github.daggerok

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AppTest {

  @Test(expected = IllegalStateException::class)
  fun `Enabled counter aggregate cannot be enabled`() {

    CounterAggregate()
        .on(CounterEnabledEvent())
        .on(CounterEnabledEvent())
  }

  @Test(expected = IllegalStateException::class)
  fun `Disabled counter cannot be incremented`() {

    CounterAggregate()
        .on(CounterEnabledEvent())
        .on(CounterDisabledEvent())
        .on(CounterIncrementedEvent())
  }

  @Test(expected = IllegalStateException::class)
  fun `Disabled counter cannot be decremented`() {

    CounterAggregate()
        .on(CounterEnabledEvent())
        .on(CounterDisabledEvent())
        .on(CounterDecrementedEvent())
  }

  @Test(expected = IllegalStateException::class)
  fun `Disabled counter aggregate cannot be diabled`() {

    CounterAggregate()
        .on(CounterEnabledEvent())
        .on(CounterDisabledEvent())
        .on(CounterDisabledEvent())
  }

  @Test
  fun `DomainEvents should restore Aggregate from list of events and snapshot`() {

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
  fun `DomainEvents should recreate Aggregate from list of events`() {

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
  fun `CounterAggregate should apply list of domain events to rebuild own state`() {

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

    val aggregate2 = CounterAggregate()
    val result2 = FoldJava.foldLeft(aggregate2, *events.toTypedArray())
    assertNotNull(result2)
    assertTrue(result2.isPresent)
    assertEquals(result2.get().counter, 1)

    assertEquals(result1, result2.get())
    println("result1: $result1")
    println("result2: ${result2.get()}")

  }
}
