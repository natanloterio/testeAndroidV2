package br.com.hbsis.testeandroidnatan.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

public class MediaScannerWrapper implements MediaScannerConnection.MediaScannerConnectionClient {
    private MediaScannerConnection mConnection;
    private String mPath;
    private String mMimeType;

    public MediaScannerWrapper(Context ctx, String filePath){
        mPath = filePath;
        mMimeType = "*/*";
        mConnection = new MediaScannerConnection(ctx, this);
    }

    public void scan() {
        mConnection.connect();
    }

    public void onMediaScannerConnected() {
        mConnection.scanFile(mPath, mMimeType);
    }

    public void onScanCompleted(String path, Uri uri) {
    }
}