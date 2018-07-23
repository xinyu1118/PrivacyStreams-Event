package io.github.privacystreamsevents.io;

import android.Manifest;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.DropboxUtils;

/**
 * Upload an item to Dropbox
 */

final class PSDropboxUploader<Tin> extends PSFileWriter<Tin> {

    PSDropboxUploader(Function<Tin, String> filePathGenerator, boolean append) {
        super(filePathGenerator, false, append);
        this.addRequiredPermissions(Manifest.permission.INTERNET);
    }

    @Override
    public void applyInBackground(UQI uqi, Tin input) {
        super.applyInBackground(uqi, input);
        DropboxUtils.addToWaitingList(uqi, this.validFilePath);
        DropboxUtils.syncFiles(uqi, this.append);
    }
}
