package com.github.daggerok.callbackhell;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Data;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

@Data
class Ad {

    private final String content;
}

interface Promise {
    void complete(Ad ad);
}

@Data
class MyPromise implements Promise {

    private final Ad value;

    @Override
    public void complete(Ad ad) {
        System.out.printf("Completing Ad(%s)...%n", ad);
        if (Objects.isNull(ad)) return;
        System.out.printf("Ad(%s) completed.%n", ad);
    }
}

interface Mediation {
    <P extends Promise> void mediate(P promise);
}

@Data
class MyMediation implements Mediation {

    private final AtomicLong mediateCounter = new AtomicLong();
    private final AtomicLong myPromiseCounter = new AtomicLong();

    @Override
    public <P extends Promise> void mediate(P promise) {
        System.out.printf("Start Promise(%s) mediation...%n", promise);
        if (Objects.isNull(promise)) return;
        doMediation(promise);
        System.out.printf("Promise(%s) mediation completed.%n", promise);
    }

    private <P extends Promise> void doMediation(Promise promise) {
        System.out.printf("Process mediation...%n");
        if (Objects.isNull(promise)) return;
        mediateCounter.incrementAndGet();
        System.out.printf("Process %s mediation...%n", promise);
        if (promise instanceof MyPromise) {
            Ad value = ((MyPromise) promise).getValue();
            myPromiseCounter.incrementAndGet();
            System.out.printf("MyPromise mediation content: %s%n", value.getContent());
        }
    }
}

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CallbackHellTests {

    @Test
    void should_test() {
        // given
        Ad ad = new Ad("My ad");
        MyPromise myPromise = new MyPromise(ad);
        MyMediation myMediation = new MyMediation();

        // when
        myMediation.mediate(myPromise);

        // then
        assertThat(myMediation.getMediateCounter().get(), IsEqual.equalTo(1L));
        assertThat(myMediation.getMyPromiseCounter().get(), IsEqual.equalTo(1L));
    }
}
