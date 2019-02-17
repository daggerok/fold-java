package com.github.daggerok

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object CounterFailedSpec : Spek({
  describe("Counter aggregate") {

    it("Not enabled counter cannot be incremented.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterIncrementedEvent())
      }
      assertEquals("Disabled counter cannot be incremented.", result.localizedMessage)
    }

    it("Not enabled counter cannot be decremented.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterDecrementedEvent())
      }
      assertEquals("Disabled counter cannot be decremented.", result.localizedMessage)
    }

    it("Counter was already enabled.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterEnabledEvent())
            .on(CounterEnabledEvent())
      }
      assertEquals("Counter was already enabled.", result.localizedMessage)
    }

    it("Disabled counter cannot be incremented.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterEnabledEvent())
            .on(CounterDisabledEvent())
            .on(CounterIncrementedEvent())
      }
      assertEquals("Disabled counter cannot be incremented.", result.localizedMessage)
    }

    it("Disabled counter cannot be decremented.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterEnabledEvent())
            .on(CounterDisabledEvent())
            .on(CounterDecrementedEvent())
      }
      assertEquals("Disabled counter cannot be decremented.", result.localizedMessage)
    }

    it("Disabled counter cannot be disabled again.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterDisabledEvent())
      }
      assertEquals("Disabled counter cannot be disabled.", result.localizedMessage)
    }

    it("Disabled counter cannot be disabled again.") {
      val aggregate = CounterAggregate()
          .on(CounterEnabledEvent())
          .on(CounterIncrementedEvent())
          .on(CounterIncrementedEvent())
          .on(CounterDecrementedEvent())
          .on(CounterIncrementedEvent())
          .on(CounterIncrementedEvent())
          .on(CounterDisabledEvent())
      assertEquals(3, aggregate.counter)
    }
  }
})
