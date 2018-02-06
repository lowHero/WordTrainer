package com.nz2dev.wordtrainer.domain.device;

import com.nz2dev.wordtrainer.domain.models.WordsPacket;

import java.io.IOException;

/**
 * Created by nz2Dev on 11.01.2018
 */
public interface Importer {

    WordsPacket importWordsPacket(String path) throws IOException;

}
