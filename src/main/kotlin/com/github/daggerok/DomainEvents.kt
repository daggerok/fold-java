@file:JvmName("DomainEvents")

package com.github.daggerok

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

fun Collection<DomainEvent>.recreate(): CounterAggregate = this
    .onEach { println(it.type) }
    .fold(CounterAggregate()) { acc, domainEvent ->
      when (domainEvent) {
        is CounterCreatedEvent -> acc.on(domainEvent)
        is CounterIncrementedEvent -> acc.on(domainEvent)
        is CounterDecrementedEvent -> acc.on(domainEvent)
        else -> throw IllegalStateException("unexpected unsupported domain event occur: $domainEvent")
      }
    }

fun Collection<DomainEvent>.restoreFromSnapshot(snapshot: CounterAggregate): CounterAggregate = this
    .fold(snapshot) { acc, domainEvent ->
      println(domainEvent.type)
      return@fold when (domainEvent) {
        is CounterCreatedEvent -> acc.on(domainEvent)
        is CounterIncrementedEvent -> acc.on(domainEvent)
        is CounterDecrementedEvent -> acc.on(domainEvent)
        else -> throw IllegalStateException("unexpected unsupported domain event occur: $domainEvent")
      }
    }
