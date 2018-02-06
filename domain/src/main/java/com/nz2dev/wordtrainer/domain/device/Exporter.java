package com.nz2dev.wordtrainer.domain.device;

import com.nz2dev.wordtrainer.domain.models.WordsPacket;

import java.io.IOException;

/**
 * Created by nz2Dev on 11.01.2018
 */
public interface Exporter {

    String WORDS_PACK_EXTENSION = ".wrds";

    void exportWordsPacket(String fileName, WordsPacket packet) throws IOException;

}
