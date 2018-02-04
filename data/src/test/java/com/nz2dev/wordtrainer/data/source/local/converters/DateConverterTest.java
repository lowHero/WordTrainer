package com.nz2dev.wordtrainer.data.source.local.converters;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nz2Dev on 17.12.2017
 */
public class DateConverterTest {

    @Test
    public void convert_NullDateToLong_ShouldReturnZero() {
        long result = DateConverter.fromDate(null);

        assertThat(result).isZero();
    }

}
