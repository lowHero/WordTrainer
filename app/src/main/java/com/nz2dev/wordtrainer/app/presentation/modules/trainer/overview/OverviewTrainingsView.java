package com.nz2dev.wordtrainer.app.presentation.modules.trainer.overview;

import com.nz2dev.wordtrainer.domain.models.Training;
import com.nz2dev.wordtrainer.domain.models.Word;

import java.util.Collection;

/**
 * Created by nz2Dev on 30.11.2017
 */
public interface OverviewTrainingsView {

    void showTrainings(Collection<Training> words);

}
