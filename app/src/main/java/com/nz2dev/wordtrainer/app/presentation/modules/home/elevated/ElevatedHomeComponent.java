package com.nz2dev.wordtrainer.app.presentation.modules.home.elevated;

import com.nz2dev.wordtrainer.app.common.dependencies.scopes.ForActionsContainersCompositions;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeComponent;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeFragment;
import com.nz2dev.wordtrainer.app.presentation.modules.home.HomeModule;

import dagger.Subcomponent;

/**
 * Created by nz2Dev on 17.01.2018
 */
@ForActionsContainersCompositions
@Subcomponent
public interface ElevatedHomeComponent {

    void inject(HomeFragment homeFragment);

    HomeComponent createHomeComponent(HomeModule homeModule);

}
