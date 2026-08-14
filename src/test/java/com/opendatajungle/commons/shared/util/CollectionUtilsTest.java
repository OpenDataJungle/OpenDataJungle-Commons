package com.opendatajungle.commons.shared.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {

    @Test
    void isEmpty_shouldReturnTrue_whenCollectionIsNullOrEmpty() {
        assertThat(CollectionUtils.isEmpty(null)).isTrue();
        assertThat(CollectionUtils.isEmpty(List.of())).isTrue();
    }

    @Test
    void isEmpty_shouldReturnFalse_whenCollectionHasElements() {
        assertThat(CollectionUtils.isEmpty(List.of("a"))).isFalse();
    }

    @Test
    void isNotEmpty_shouldReturnFalse_whenCollectionIsNullOrEmpty() {
        assertThat(CollectionUtils.isNotEmpty(null)).isFalse();
        assertThat(CollectionUtils.isNotEmpty(List.of())).isFalse();
    }

    @Test
    void isNotEmpty_shouldReturnTrue_whenCollectionHasElements() {
        assertThat(CollectionUtils.isNotEmpty(List.of("a"))).isTrue();
    }

    @Test
    void emptyIfNull_shouldReturnEmptyList_whenListIsNull() {
        assertThat(CollectionUtils.emptyIfNull(null)).isEmpty();
    }

    @Test
    void emptyIfNull_shouldReturnSameList_whenListIsNotNull() {
        List<String> list = List.of("a", "b");

        assertThat(CollectionUtils.emptyIfNull(list)).isSameAs(list);
    }
}
