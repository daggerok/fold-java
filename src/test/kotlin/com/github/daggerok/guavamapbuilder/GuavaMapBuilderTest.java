package com.github.daggerok.guavamapbuilder;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GuavaMapBuilderTest {

    @Test
    void should_use_guava_empty_map_builder() {
        // given
        var empty = ImmutableMap.builder()
                .build();

        // then
        assertThat(empty).isEmpty();
    }

    @Test
    void should_use_guava_non_empty_map_builder() {
        // given
        var empty = ImmutableMap.builder()
                .put("ololo 1", "trololo 1")
                .put("ololo 2", "trololo 2")
                .put("ololo 3", "trololo 3")
                .put("ololo 4", "trololo 4")
                .put("ololo 5", "trololo 5")
                .put("ololo 6", "trololo 6")
                .put("ololo 7", "trololo 7")
                .put("ololo 8", "trololo 8")
                .put("ololo 9", "trololo 9")
                .put("ololo 10", "trololo 10")
                .put("ololo 11", "trololo 11")
                .put("ololo 12", "trololo 12")
                .put("ololo 13", "trololo 13")
                .put("ololo 14", "trololo 14")
                .put("ololo 15", "trololo 15")
                .put("ololo 16", "trololo 16")
                .put("ololo 17", "trololo 17")
                .put("ololo 18", "trololo 18")
                .put("ololo 19", "trololo 19")
                .put("ololo 20", "trololo 20")
                .build();

        // then
        assertThat(empty).isNotEmpty().hasSizeGreaterThan(15);
    }
}
