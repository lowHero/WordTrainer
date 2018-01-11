package com.nz2dev.wordtrainer.domain.models.bindings;

/**
 * Created by nz2Dev on 10.01.2018
 */
public abstract class BindableModel {

    protected static boolean checkEmbedded(EmbeddedModel embeddedModel) {
        return embeddedModel != null && embeddedModel.isQuiteDefined();
    }

    public abstract boolean isFullSpecified();

}
