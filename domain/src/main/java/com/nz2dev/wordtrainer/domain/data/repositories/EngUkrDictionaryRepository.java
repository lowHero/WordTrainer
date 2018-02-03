package com.nz2dev.wordtrainer.domain.data.repositories;

import io.reactivex.Single;

/**
 * Created by nz2Dev on 01.02.2018
 */
public interface EngUkrDictionaryRepository {

    Single<String> getUkrainianTranslation(String englishText);

}
