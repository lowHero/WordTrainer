package com.nz2dev.wordtrainer.device.files;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.nz2dev.wordtrainer.device.files.serialization.JsonWordsPacket;
import com.nz2dev.wordtrainer.device.files.serialization.JsonWordData;
import com.nz2dev.wordtrainer.domain.environtment.Exporter;
import com.nz2dev.wordtrainer.domain.models.Word;
import com.nz2dev.wordtrainer.domain.models.internal.WordData;
import com.nz2dev.wordtrainer.domain.models.internal.WordsPacket;
import com.nz2dev.wordtrainer.domain.utils.ultralightmapper.UltraLightMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nz2Dev on 11.01.2018
 */
@Singleton
public class AndroidJsonExporter implements Exporter {

    private final Gson gson;
    private final UltraLightMapper mapper;
    private final Context context;

    @Inject
    public AndroidJsonExporter(Context context) {
        this.context = context;
        this.gson = new Gson();
        this.mapper = new UltraLightMapper() {
            @Override
            protected void configure() {
                forSource(WordData.class).map(source -> new JsonWordData(source.original, source.translation));
            }
        };
    }

    @Override
    public void exportWordsPacket(String fileName, WordsPacket wordsPacket) throws IOException {
        String jsonOutput = gson.toJson(toJsonWordsPacket(wordsPacket));
        File exportDirectory = getDirectory();
        File exportedFile = new File(exportDirectory, fileName + WORDS_PACK_EXTENSION);

        FileOutputStream outputStream = new FileOutputStream(exportedFile);
        outputStream.write(jsonOutput.getBytes());
        outputStream.close();
    }

    private File getDirectory() {
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            if (!docsFolder.mkdir()) {
                Log.e("Export", "docs dir not created");
            }
        }

        File exportedFolder = new File(docsFolder.getAbsolutePath(), "/Words");
        if (!exportedFolder.exists()) {
            if (!exportedFolder.mkdir()) {
                Log.e("Export", "wordsData dir not created");
            }
        }
        return exportedFolder;
    }

    private JsonWordsPacket toJsonWordsPacket(WordsPacket wordsPacket) {
        Collection<JsonWordData> jsonWordsData = mapper.mapList(wordsPacket.wordsData, new ArrayList<>(wordsPacket.wordsData.size()), JsonWordData.class);

        return new JsonWordsPacket(
                wordsPacket.originalLanguageKey,
                wordsPacket.translationlanguageKey,
                jsonWordsData);
    }

}
