package com.nz2dev.wordtrainer.app.presentation.modules.word.explore;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.PerActivity;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 13.01.2018
 */
@PerActivity
@Subcomponent
public interface ExploreWordsSourceComponent {
    void inject(ExploreWordsSourceFragment exploreWordsSourceFragment);
}
