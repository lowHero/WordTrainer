package com.nz2dev.wordtrainer.app.presentation.modules.word.explore.elevated;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.ForActions;
import com.nz2dev.wordtrainer.app.presentation.modules.word.explore.ExploreWordsSourceFragment;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 13.01.2018
 */
@ForActions
@Subcomponent
public interface ElevatedExploreWordsSourceComponent {
    void inject(ExploreWordsSourceFragment exploreWordsSourceFragment);
}
