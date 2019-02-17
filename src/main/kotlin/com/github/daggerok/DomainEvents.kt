@file:JvmName("DomainEvents")

package com.github.daggerok

interface DomainEvent {
  val type: Class<out DomainEvent>
    get() = this.javaClass
}

class CounterEnabledEvent : DomainEvent
class CounterIncrementedEvent : DomainEvent
class CounterDecrementedEvent : DomainEvent
class CounterDisabledEvent : DomainEvent

fun CounterAggregate.apply(event: DomainEvent): CounterAggregate = when (event) {
  is CounterEnabledEvent -> this.on(event)
  is CounterIncrementedEvent -> this.on(event)
  is CounterDecrementedEvent -> this.on(event)
  is CounterDisabledEvent -> this.on(event)
  else -> throw IllegalStateException("unexpected unsupported domain event occur: $event")
}

fun Collection<DomainEvent>.recreate(): CounterAggregate = this
    .onEach { println(it.type) }
    .fold(CounterAggregate()) { acc, domainEvent ->
      when (domainEvent) {
        is CounterEnabledEvent -> acc.on(domainEvent)
        is CounterIncrementedEvent -> acc.on(domainEvent)
        is CounterDecrementedEvent -> acc.on(domainEvent)
        is CounterDisabledEvent -> acc.on(domainEvent)
        else -> throw IllegalStateException("unexpected unsupported domain event occur: $domainEvent")
      }
    }

fun Collection<DomainEvent>.restoreFromSnapshot(snapshot: CounterAggregate): CounterAggregate = this
    .fold(snapshot) { acc, domainEvent ->
      println(domainEvent.type)
      return@fold when (domainEvent) {
        is CounterEnabledEvent -> acc.on(domainEvent)
        is CounterIncrementedEvent -> acc.on(domainEvent)
        is CounterDecrementedEvent -> acc.on(domainEvent)
        is CounterDisabledEvent -> acc.on(domainEvent)
        else -> throw IllegalStateException("unexpected unsupported domain event occur: $domainEvent")
      }
    }
