package com.github.daggerok

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object CounterSpec : Spek({
  describe("Counter aggregate") {

    it("should not be incremented when not enabled.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterIncrementedEvent())
      }
      assertEquals("Disabled counter cannot be incremented.", result.localizedMessage)
    }

    it("should not be decremented when it's not enabled.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterDecrementedEvent())
      }
      assertEquals("Disabled counter cannot be decremented.", result.localizedMessage)
    }

    it("should not enable when it's already enabled.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterEnabledEvent())
            .on(CounterEnabledEvent())
      }
      assertEquals("Counter was already enabled.", result.localizedMessage)
    }

    it("should not be incremented when it's disabled.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterEnabledEvent())
            .on(CounterDisabledEvent())
            .on(CounterIncrementedEvent())
      }
      assertEquals("Disabled counter cannot be incremented.", result.localizedMessage)
    }

    it("should not be decremented when it's disabled.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterEnabledEvent())
            .on(CounterDisabledEvent())
            .on(CounterDecrementedEvent())
      }
      assertEquals("Disabled counter cannot be decremented.", result.localizedMessage)
    }

    it("should not be disabled when it was already disabled.") {
      val result = assertFailsWith<IllegalStateException> {
        CounterAggregate()
            .on(CounterDisabledEvent())
      }
      assertEquals("Disabled counter cannot be disabled.", result.localizedMessage)
    }

    it("should be okay...") {
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
