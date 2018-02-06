package com.nz2dev.wordtrainer.device.files;

import com.google.gson.Gson;
import com.nz2dev.wordtrainer.device.files.serialization.JsonWordData;
import com.nz2dev.wordtrainer.device.files.serialization.JsonWordsPacket;
import com.nz2dev.wordtrainer.domain.device.Importer;
import com.nz2dev.wordtrainer.domain.models.WordData;
import com.nz2dev.wordtrainer.domain.models.WordsPacket;
import com.nz2dev.wordtrainer.domain.utils.ultralightmapper.UltraLightMapper;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 11.01.2018
 */
@Singleton
public class AndroidJsonImporter implements Importer {

    private final Gson gson;
    private final UltraLightMapper mapper;

    @Inject
    public AndroidJsonImporter() {
        gson = new Gson();
        mapper = new UltraLightMapper() {
            @Override
            protected void configure() {
                forSource(JsonWordData.class).map(source -> new WordData(source.original, source.translation));
            }
        };
    }

    @Override
    public WordsPacket importWordsPacket(String path) throws IOException {
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);

        String jsonRawInput = IOUtils.toString(inputStream);
        IOUtils.closeQuietly(inputStream);

        JsonWordsPacket jsonWordsPacket = gson.fromJson(jsonRawInput, JsonWordsPacket.class);
        return toWordsPacket(jsonWordsPacket);
    }

    private WordsPacket toWordsPacket(JsonWordsPacket jsonWordsPacket) {
        Collection<WordData> words = mapper.mapList(jsonWordsPacket.wordsData,
                new ArrayList<>(jsonWordsPacket.wordsData.size()), WordData.class);

        return new WordsPacket(
                jsonWordsPacket.originalLanguageKey,
                jsonWordsPacket.translationLanguageKey,
                words);
    }

}
